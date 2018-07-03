package ua.com.fart.sqlcmd.model;

import java.sql.Connection;

public interface DatabaseManager {
    void connect(String user, String password, String database);

    Connection getConnection();

    DataSet[] getTableData(String tableName);

    String[] getTableNames();

    int getSize(String tableName);

    void clear(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    String[] getTableColumns(String tableName);

    boolean isConnected();
}
