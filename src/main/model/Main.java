import java.sql.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] argv) throws SQLException {
        String user = "root";
        String password = "111111";
        String database = "postgres";

        DatabaseManager manager = new JDBCDatabaseManager();
        manager.connect(user, password, database);
        Connection connection = manager.getConnection();

        try {
            //CLEAR
            manager.clear("user");

            //INSERT
            DataSet data = new DataSet();
            data.put("id", 15);
            data.put("name", "Stiven");
            data.put("password", "pass");
            manager.create("user", data);

            //SELECT-A
            String[] tables = manager.getTableNames();
            System.out.println("List databases: "+ Arrays.toString(tables));

            //SELECT-B
            String tableName = "user";
            DataSet[] result = manager.getTableData(tableName);
            System.out.println(Arrays.toString(result));

            //UPDATE
            DataSet newValeue = new DataSet();
            newValeue.put("password", "fhdhfjfjjfjf");
            manager.update("user",15, newValeue);
            System.out.println();

            String[] columnNames = manager.getTableColumns("user");
            System.out.println("Head of table:\n"+Arrays.toString(columnNames));

            connection.close();
        }catch (SQLException e){e.printStackTrace();}
    }
}