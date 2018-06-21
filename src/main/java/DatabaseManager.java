import java.sql.*;
import java.util.Random;

public class DatabaseManager {
    private static Connection getConnection(String user, String password, String database) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection("jdbc:postgresql://localhost:5432/"+database, user, password);
    }
        public static void main(String[] argv) throws ClassNotFoundException, SQLException {
            String user = "postgres";
            String password = "111111";
            String database = "postgres";

            Connection connection = getConnection(user, password, database);

            //INSERT
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("INSERT INTO public.user (name, password)  VALUES ('STIVEN', '0123456789')");

            //SELECT
            stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM postgres");
            while (rs.next()) {
                System.out.print(rs);

            }
            rs.close();
            stmt.close();

            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM public.user WHERE id > 5");
            while (rs.next()) {
                System.out.print("id "+ (rs.getString(1)) + ", ");
                System.out.print("name " + (rs.getString(2)) + ", ");
                System.out.println("password " + (rs.getString(3)));
            }
            rs.close();
            stmt.close();

            //DELETE
            stmt = connection.createStatement();
            stmt.executeUpdate("DELETE FROM public.user WHERE id > 10 and id <20");
            stmt.close();

            //UPDATE
            PreparedStatement ps = connection.prepareStatement("UPDATE public.user SET password = ? WHERE id > 6");
            ps.setString(1, String.valueOf(new Random().nextInt()));
            ps.executeUpdate();
            ps.close();

            connection.close();
        }
}

