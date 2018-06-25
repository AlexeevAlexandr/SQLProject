import java.sql.*;
import java.util.*;

public class JDBCDatabaseManager implements DatabaseManager {
    private Connection connection;

    @Override
    public void connect(String user, String password, String database) throws SQLException{
        try { //check driver
            Class.forName("org.postgresql.Driver");
        }catch(ClassNotFoundException e){
            System.out.println("Couldn't find the JDBC driver");
            e.printStackTrace();}

         //connection
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/"
                    +database, user, password);

    }

    @Override
    public Connection getConnection() {
        return connection;
    }
    //SELECT-B
    @Override
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

    //SELECT-A
    @Override
    public String[] getTableNames(){
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


    @Override
    public int getSize(String tableName){
        try {
            Statement stmt = connection.createStatement();
            ResultSet rsCount = stmt.executeQuery("SELECT COUNT(*) FROM public." + tableName);
            rsCount.next();
            int size = rsCount.getInt(1);
            rsCount.close();
            return size;
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    //CLEAR
    @Override
    public void clear(String tableName){
        try{
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public."+tableName);
            stmt.close();
        }catch(SQLException e){e.printStackTrace();}
    }

    //INSERT
    @Override
    public void create(String tableName, DataSet input) {
        try {
            Statement stmt = connection.createStatement();

            String tableNames = getNameFormated(input, "%s,");
            String values = getValuesFormated(input,"'%s',");

            stmt.executeUpdate("INSERT INTO public." + tableName + " (" + tableNames + ")" +
                    "VALUES (" + values + ")");
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //UPDATE
    @Override
    public void update(String tableName, int id, DataSet newValue) {
        try{
            String tableNames = getNameFormated(newValue, "%s = ?,");

            PreparedStatement ps = connection.prepareStatement(
                    "UPDATE public."+tableName+" SET " + tableNames + " WHERE id = ?");
            int index = 1;
            for (Object value : newValue.getValues()){
                ps.setObject(index++, value);
            }
            ps.setInt(index, id);
            ps.executeUpdate();
            ps.close();
        }catch(Exception e){e.printStackTrace();}
    }


     private String getNameFormated(DataSet newValue, String format) {
        StringBuilder string = new StringBuilder();
        for (String name : newValue.getNames()) {
            string.append(String.format(format, name));
        }
        string = new StringBuilder(string.substring(0, string.length() - 1));
        return string.toString();
    }


     private String getValuesFormated(DataSet input, String format) {
        StringBuilder values = new StringBuilder();
        for (Object value: input.getValues()) {
            values.append(String.format(format, value));
        }
        values = new StringBuilder(values.substring(0, values.length() - 1));
        return values.toString();
    }


    @Override
    public String[] getTableColumns(String tableName) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM information_schema.columns WHERE table_schema = 'public' AND table_name = '" + tableName + "'");
            String[] tables = new String[100];
            int index = 0;
            while (rs.next()) {
                tables[index++] = rs.getString("column_name");
            }
            tables = Arrays.copyOf(tables, index, String[].class);
            rs.close();
            stmt.close();
            return tables;
        } catch (SQLException e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}