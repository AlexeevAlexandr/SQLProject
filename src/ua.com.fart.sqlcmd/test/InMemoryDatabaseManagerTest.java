package ua.com.fart.sqlcmd.test;

import org.junit.Before;
import ua.com.fart.sqlcmd.model.JDBCDatabaseManager;

import java.sql.SQLException;

public class InMemoryDatabaseManagerTest extends JDBCDatabaseManagerTest {
    @Before
    public void setup () throws SQLException {
        manager = new JDBCDatabaseManager();
        manager.connect("root", "111111", "postgres");
    }
}
