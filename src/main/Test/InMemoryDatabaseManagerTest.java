import org.junit.Before;

public class InMemoryDatabaseManagerTest extends JDBCDatabaseManagerTest{
    @Before
    public void setup (){
        manager = new JDBCDatabaseManager();
        manager.connect("root", "111111", "postgres");
    }
}
