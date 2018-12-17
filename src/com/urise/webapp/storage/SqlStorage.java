package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;
    private static final Logger lOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.sqlExecute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        lOG.info("Update " + resume);
        sqlHelper.sqlTransactionalExecute(conn -> {
            final String uuid = resume.getUuid();
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, uuid);
                executeUpdate(uuid, ps);
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact c WHERE c.resume_uuid = ?")) {
                ps.setString(1, uuid);
                ps.execute();
            }
            insertContact(resume, conn);
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section s WHERE s.resume_uuid = ?")) {
                ps.setString(1, uuid);
                ps.execute();
            }
            insertSection(resume, conn);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        lOG.info("Save " + resume);
        sqlHelper.sqlTransactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES(?, ?)")) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            }
            insertContact(resume, conn);
            insertSection(resume, conn);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        lOG.info("Get " + uuid);
        return sqlHelper.sqlTransactionalExecute(conn -> {
            try (PreparedStatement ps1 = conn.prepareStatement("" +
                    "SELECT *\n" +
                    "FROM resume r\n" +
                    "  LEFT JOIN contact c ON r.uuid = c.resume_uuid\n" +
                    "WHERE r.uuid = ?");
                 PreparedStatement ps2 = conn.prepareStatement("" +
                         "SELECT *\n" +
                         "FROM section s\n" +
                         "WHERE s.resume_uuid = ?")) {
                ps1.setString(1, uuid);
                ResultSet rs1 = ps1.executeQuery();
                if (!rs1.next()) {
                    lOG.warning("Resume " + uuid + " not exist");
                    throw new NotExistStorageException(uuid);
                }
                Resume resume = new Resume(uuid, rs1.getString("full_name"));
                do {
                    addContact(resume, rs1);
                } while (rs1.next());
                ps2.setString(1, uuid);
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    addSection(resume, rs2);
                }
                return resume;
            }
        });
    }

    @Override
    public void delete(String uuid) {
        lOG.info("Delete " + uuid);
        sqlHelper.<Void>sqlExecute("DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            executeUpdate(uuid, ps);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        lOG.info("GetAllSorted");
        Map<String, Resume> mapResume = new LinkedHashMap<>();
        return sqlHelper.sqlTransactionalExecute(conn -> {
            try (PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name, uuid");
                 PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM contact");
                 PreparedStatement ps3 = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    mapResume.put(rs1.getString("uuid"), new Resume(rs1.getString("uuid"), rs1.getString("full_name")));
                }
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    addContact(mapResume.get(rs2.getString("resume_uuid")), rs2);
                }
                ResultSet rs3 = ps3.executeQuery();
                while (rs3.next()) {
                    addSection(mapResume.get(rs3.getString("resume_uuid")), rs3);
                }
            }
            return new ArrayList<>(mapResume.values());
        });
    }

    @Override
    public int size() {
        return sqlHelper.sqlExecute("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private void insertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact(resume_uuid, type, value) VALUES(?, ?, ?)")) {
            final Map<ContactType, String> contacts = resume.getContacts();
            final String uuid = resume.getUuid();
            for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                ps.setString(1, uuid);
                ps.setString(2, contact.getKey().name());
                ps.setString(3, contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void insertSection(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section(resume_uuid, type, value) VALUES(?, ?, ?)")) {
            final Map<SectionType, AbstractSection> sections = resume.getSections();
            final String uuid = resume.getUuid();
            for (Map.Entry<SectionType, AbstractSection> section : sections.entrySet()) {
                final SectionType type = section.getKey();
                ps.setString(1, uuid);
                ps.setString(2, type.name());
                switch (type) {
                    case OBJECTIVE:
                    case PERSONAL:
                        final TextSection textSection = (TextSection) section.getValue();
                        ps.setString(3, textSection.getContent());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        final ListSection listSection = (ListSection) section.getValue();
                        final List<String> items = listSection.getItems();
                        String value = String.join("\n", items);
                        ps.setString(3, value);
                        break;
                }
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        final String type = rs.getString("type");
        if (type != null) {
            resume.addContact(ContactType.valueOf(type), rs.getString("value"));
        }
    }

    private void addSection(Resume resume, ResultSet rs) throws SQLException {
        final SectionType type = SectionType.valueOf(rs.getString("type"));
        switch (type) {
            case OBJECTIVE:
            case PERSONAL:
                resume.addSection(type, new TextSection(rs.getString("value")));
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                List<String> items = Arrays.asList(rs.getString("value").split("\n"));
                resume.addSection(type, new ListSection(items));
                break;
        }
    }

    private void executeUpdate(String uuid, PreparedStatement ps) throws SQLException {
        if (ps.executeUpdate() == 0) {
            lOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
    }
}
