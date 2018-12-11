package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                ps.setString(1, resume.getFullName());
                ps.setString(2, resume.getUuid());
                executeUpdate(resume.getUuid(), ps);
            }
            try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact c WHERE c.resume_uuid = ?")) {
                ps.setString(1, resume.getUuid());
                ps.execute();
            }
            insertContact(resume, conn);
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
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        lOG.info("Get " + uuid);
        return sqlHelper.sqlExecute("" +
                "SELECT *\n" +
                "FROM resume r\n" +
                "  LEFT JOIN contact c ON r.uuid = c.resume_uuid\n" +
                "WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                lOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, rs.getString("full_name"));
            do {
                addContact(resume, rs);
            } while (rs.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        lOG.info("Delete " + uuid);
        sqlHelper.sqlExecute("DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            executeUpdate(uuid, ps);
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        lOG.info("GetAllSorted");
        List<Resume> resumes = new ArrayList<>();
        sqlHelper.sqlTransactionalExecute(conn -> {
            try (PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM resume r ORDER BY full_name, uuid");
                 PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM contact c")) {
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    Resume resume = new Resume(rs1.getString("uuid"), rs1.getString("full_name"));
                    resumes.add(resume);
                }
                Map<String, String> mapContact = new HashMap<>();
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    mapContact.put(rs2.getString("resume_uuid") + rs2.getString("type"), rs2.getString("value"));
                }
                for (int i = 0; i < resumes.size(); i++) {
                    Resume resume = resumes.get(i);
                    for (Map.Entry<String, String> contact : mapContact.entrySet()) {
                        String uuid = contact.getKey().substring(0, 36);
                        if (resume.getUuid().equals(uuid)) {
                            resume.addContact(ContactType.valueOf(contact.getKey().substring(36)), contact.getValue());
                        }
                    }
                    resumes.set(i, resume);
                }
            }
            return null;
        });
        return resumes;
    }

    @Override
    public int size() {
        return sqlHelper.sqlExecute("SELECT COUNT(*) FROM resume r", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        });
    }

    private void insertContact(Resume resume, Connection conn) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact(resume_uuid, type, value) VALUES(?, ?, ?)")) {
            final Map<ContactType, String> contacts = resume.getContacts();
            for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                ps.setString(1, resume.getUuid());
                ps.setString(2, contact.getKey().name());
                ps.setString(3, contact.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addContact(Resume resume, ResultSet rs) throws SQLException {
        if (rs.getString("type") != null) {
            resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
        }
    }

    private void executeUpdate(String uuid, PreparedStatement ps) throws SQLException {
        if (ps.executeUpdate() == 0) {
            lOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
    }
}