package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;
import org.postgresql.util.PSQLException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorage implements Storage {
    private final SqlHelper sqlHelper;
    private static final Logger lOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.sqlExecute("DELETE FROM resume", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        lOG.info("Update " + resume);
        sqlHelper.sqlExecute("UPDATE resume SET full_name = ? WHERE uuid = ?", ps -> {
            ps.setString(1, resume.getFullName());
            ps.setString(2, resume.getUuid());
            if (ps.executeUpdate() == 0) {
                lOG.warning("Resume " + resume.getUuid() + " not exist");
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        lOG.info("Save " + resume);
        sqlHelper.sqlExecute("INSERT INTO resume(uuid, full_name) VALUES(?, ?)", ps -> {
            try {
                ps.setString(1, resume.getUuid());
                ps.setString(2, resume.getFullName());
                ps.execute();
            } catch (PSQLException e) {
                if (e.getSQLState().equals("23505")) {
                    lOG.warning("Resume " + resume.getUuid() + " already exist");
                    throw new ExistStorageException(resume.getUuid());
                }
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        lOG.info("Get " + uuid);
        return sqlHelper.sqlExecute("SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                lOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        lOG.info("Delete " + uuid);
        sqlHelper.sqlExecute("DELETE FROM resume r WHERE r.uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                lOG.warning("Resume " + uuid + " not exist");
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        lOG.info("GetAllSorted");
        return sqlHelper.sqlExecute("SELECT trim(uuid) AS uuid, full_name FROM resume r ORDER BY full_name, uuid", ps -> {
            List<Resume> resumes = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelper.sqlExecute("SELECT COUNT(*) AS cnt FROM resume r", ps -> {
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt("cnt");
        });
    }
}
