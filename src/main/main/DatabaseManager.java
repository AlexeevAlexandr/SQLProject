import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private Connection connection;
    void connect(String user, String password, String database) {
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
    //SELECT-A
    String[] getTableNames(){
        Statement stmt;
        try {
            stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT table_name FROM information_schema.tables WHERE table_schema='public'");
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
    //SELECT-B
    DataSet[] getTableData(String tableName) {
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
    //CLEAR
    void clear(String tableName){
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public."+tableName);
            stmt.close();
        }catch(SQLException e){e.printStackTrace();}
    }
    //INSERT
    void create(DataSet input) {
        try {
            Statement stmt = connection.createStatement();
            StringBuilder tableNames = new StringBuilder();
            String values;

            for (String name : input.getNames()) {
                tableNames.append(name).append(",");
            }
            tableNames = (new StringBuilder(tableNames.substring(0, tableNames.length() - 1)));

            StringBuilder valuesBuilder = new StringBuilder();
            for (Object value : input.getValues()) {
                valuesBuilder.append("'").append(value.toString()).append("',");
            }
            values = valuesBuilder.toString();
            values = values.substring(0,values.length()-1);

            stmt.executeUpdate("INSERT INTO public.user (" + tableNames.toString() + ") VALUES (" + values + ");");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void update(String tableName, int id, DataSet newValue) {
        try{
            String format = "% = ?,";
            StringBuilder string = getStringBuilder(newValue, format);

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE public."+tableName+" SET " + string + " WHERE id == ?");
            String pass = "password_"+new Random().nextInt();
            ps.setString(3, pass);
            ps.executeUpdate();
            ps.close();
        }catch(Exception e){e.printStackTrace();}
    }

    private StringBuilder getStringBuilder(DataSet newValue, String format) {
        StringBuilder string = new StringBuilder();
        for (String name : newValue.getNames()) {
            string.append(String.format(format, name));
        }
        string = new StringBuilder(string.substring(0, string.length() - 1));
        return string;
    }

    private Connection getConnection() {
        return connection;
    }

    public static void main(String[] argv){
        String user = "postgres";
        String password = "111111";
        String database = "postgres";

        DatabaseManager manager = new DatabaseManager();
        manager.connect(user, password, database);
        Connection connection = manager.getConnection();

        //INSERT
        try {
            DataSet data = new DataSet();
            data.put("id", 1);
            data.put("name", "Stiven");
            data.put("password", "pass");
            manager.create(data);
        }catch(Exception e){e.printStackTrace();}

        try {
        //SELECT-A
        String[] tables = manager.getTableNames();
        System.out.println("List databases: "+Arrays.toString(tables));

        //SELECT-B
        String tableName = "user";
        DataSet[] result = manager.getTableData(tableName);
        System.out.println(Arrays.toString(result));

        //UPDATE


        //CLEAR
        //manager.clear("user");

        connection.close();
        }catch (SQLException e){e.printStackTrace();}
    }
}