import java.sql.*;
import java.util.Arrays;
import java.util.Random;

public class DatabaseManager {
    private Connection connection;
    public void connect(String user, String password, String database) {
        try { //check driver
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            System.out.println("Couldn't find the JDBC driver");
            e.printStackTrace();}

        try { //check connection
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"
                    +database, user, password);
        }catch (SQLException e){
            System.out.println("Can't connect to database");
            e.printStackTrace();
            connection = null;}
    }

    public String[] getTableNames(){
        Statement stmt;
        try {
            stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("select table_name from information_schema.tables where table_schema='public'");
        String [] tables = new String[100];
        int index = 0;
        while(rs.next()) {
            tables [index++] = rs.getString("table_name");
        }
        tables = Arrays.copyOf(tables, index, String [].class);
        rs.close();
        stmt.close();
        return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    public DataSet[] getTableData(String tableName) {
        try {
            int size = getSize(tableName);
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM public." + tableName);
            ResultSetMetaData rsmd = rs.getMetaData();
            DataSet[] result = new DataSet[size];
            int index = 0;
            while (rs.next()) {
                DataSet dataSet = new DataSet();
                result[index++] = dataSet;
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    dataSet.put(rsmd.getColumnName(i), rs.getObject(i));
                }
            }
            rs.close();
            stmt.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return new DataSet[0];
        }
    }

    private int getSize(String tableName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
        rsCount.next();
        int size = rsCount.getInt(1);
        rsCount.close();
        return size;
    }

    public static void main(String[] argv){
        String user = "root";
        String password = "111111";
        String database = "postgres";

        DatabaseManager manager = new DatabaseManager();
        manager.connect(user, password, database);
        Connection connection = manager.getConnection();
        Statement stmt;

        //INSERT
        manager.create();
        try {

        //SELECT
        String[] tables = manager.getTableNames();
        System.out.println(Arrays.toString(tables));

        String tableName = "user";

            DataSet[] result = manager.getTableData(tableName);

            System.out.println(Arrays.toString(result));

        //UPDATE
        PreparedStatement ps = connection.prepareStatement("UPDATE public.user SET password = ? WHERE id > 6");
        ps.setString(1, String.valueOf(new Random().nextInt()));
        ps.executeUpdate();
        ps.close();

        manager.clear("user");

        connection.close(); }catch (SQLException e){e.printStackTrace();}
    }



    private Connection getConnection() {
        return connection;
    }
    public void clear (String tableName){
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public."+tableName);
            stmt.close();
        }catch(SQLException e){e.printStackTrace();}
    }

    public void create(DataSet input) {
        try {
            Statement stmt = connection.createStatement();
            String tableNames = "";
            String values = "";

            for (String name : input.getNames()) {
                tableNames += name + ",";
            }
            tableNames = tableNames.substring(0,tableNames.length()-1);

            for (Object value : input.getValues()) {
                values += "`" +value.toString() + "`,";
            }
            values = values.substring(0,values.length()-1);
            stmt.executeUpdate("INSERT INTO public.user (" + tableNames + ") VALUES (" + values + ")");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}