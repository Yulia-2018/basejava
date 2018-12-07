package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    public interface ExecutionProcess<T> {
        T process(PreparedStatement ps) throws SQLException;
    }

    public <T> T sqlExecute(String sql, ExecutionProcess<T> executionProcess) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executionProcess.process(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
