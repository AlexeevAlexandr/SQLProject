package ua.com.fart.sqlcmd.model;

import java.util.Set;

public interface DatabaseManager {

    void connect(String user, String password, String database);

    DataSet[] getTableData(String tableName);

    Set<String> getTableNames();

    int getSize(String tableName);

    void clear(String tableName);

    void create(String tableName, DataSet input);

    void update(String tableName, int id, DataSet newValue);

    String[] getTableColumns(String tableName);

    boolean isConnected();
}
