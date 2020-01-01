package com.jemylibs.data.seimpl.helpers;

import com.jemylibs.sedb.helpers.JavaSeLink;

import java.sql.*;
import java.util.function.Consumer;

public class MysqlHelper extends JavaSeLink {

    public static String lcs = "";
    final private String connectionSting;
    private final String userName;
    private final String password;
    private Connection connection;
    private String DBName;

    public MysqlHelper(String url, String dbname, String userName, String password) throws Exception {
        this.userName = userName;
        this.password = password;
        Class.forName("com.mysql.jdbc.Driver");
        connectionSting = "jdbc:mysql://" + url + ":3306/" + dbname + "?autoReconnect=true&useSSL=false&useUnicode=yes&characterEncoding=UTF-8&";
        this.DBName = dbname;
        lcs += connectionSting + "&user=" + userName + "&password" + password;
    }

    @Override
    public String getDbName() {
        return this.DBName;
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(connectionSting, userName, password);
        }
        try {
            getResult(connection, "select count(1) from SettingsTable;", resultSet -> {
            });

        } catch (Throwable e) {
            System.out.println("connection error : " + e.getMessage());
        }
        return connection;
    }

    private ResultSet getResult(Connection connection, String sql, Consumer<ResultSet> resultSetConsumer) throws Exception {
        Statement e = connection.createStatement();
        try {
            ResultSet res = e.executeQuery(sql);
            resultSetConsumer.accept(res);
            res.close();
            return res;
        } finally {
            e.close();
        }
    }

    @Override
    public void DeleteDbIfExists() throws SQLException {
        update("DROP DATABASE IF EXISTS " + DBName);
    }

    public void CopyTo(String FromDBName, String ToDBName, String TableName) throws SQLException {
        update("SET SQL_SAFE_UPDATES = 0;");
        update("INSERT INTO " + ToDBName + "." + TableName + " SELECT * FROM " + FromDBName + "." + TableName);
    }
}
