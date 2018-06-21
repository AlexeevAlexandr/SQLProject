import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static junit.framework.TestCase.assertEquals;

public class DatabaseManagerTest {
    private DatabaseManager manager;
        @Before
        public void setup (){
            manager = new DatabaseManager();
            manager.connect("root", "111111", "postgres");
        }
        @Test
        public void testGetAllTableNames(){
            String [] tableNames = manager.getTableNames();
            assertEquals("[user]", Arrays.toString(tableNames));
        }
        @Test
    public void testGetTableData(){
        manager.clear("user");
            DataSet [] users = manager.getTableData("users");
            assertEquals(1, users.length);

            DataSet input = new DataSet();
            input.put("id", 1);
            input.put("name", "Stiven");
            input.put("password", "pass");
            manager.create(input);

            DataSet user = users [0];
            assertEquals("[id, name, password", user.getNames());
            assertEquals("[id, name, password", user.getValues());
        }
    }
