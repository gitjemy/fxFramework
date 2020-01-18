package com.jemylibs.sedb.helpers;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteHelper extends JavaSeLink {

    private Connection connection;
    private String FilePath;

    public SQLiteHelper(String FilePath) throws Exception {
        this.FilePath = FilePath;
        Class.forName("org.sqlite.JDBC");
        openConnection();

    }

    public void openConnection() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.FilePath);
    }

    @Override
    public String getDbName() {
        return this.FilePath;
    }

    public String getFilePath() {
        return FilePath;
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    @Override
    public void DeleteDbIfExists() throws Exception {
        Files.deleteIfExists(Paths.get(FilePath));
    }
}
