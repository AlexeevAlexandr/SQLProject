import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import static junit.framework.TestCase.assertEquals;

public class DatabaseManagerTest {
    private DatabaseManager manager;
        @Before
        public void setup (){
            manager = new DatabaseManager();
            manager.connect("postgres", "111111", "postgres");
        }
        @Test
        public void testGetAllTableNames(){
            String [] tableNames = manager.getTableNames();
            assertEquals("[user]", Arrays.toString(tableNames));
        }
        @Test
        public void clearData() {
            manager.clear("user");
            DataSet[] users = manager.getTableData("user");
            assertEquals(0, users.length);
        }
        @Test
        public void testGetTableData(){
            manager.clear("user");

            DataSet input = new DataSet();
            input.put("id", 1);
            input.put("name", "Stiven");
            input.put("password", "pass");
            manager.create(input);

            DataSet[] users = manager.getTableData("user");
            DataSet user = users [0];
            assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
            assertEquals("[1, Stiven, pass]", Arrays.toString(user.getValues()));

        }
        @Test
        public void testUpdateTableData(){
            manager.clear("user");

            DataSet input = new DataSet();
            input.put("id", 11);
            input.put("name", "Stiven");
            input.put("password", "pass");
            manager.create(input);

            DataSet newValue = new DataSet();
            newValue.put("password", "pass2");
            manager.update(11, newValue);

            DataSet[] users = manager.getTableData("user");
            assertEquals(1, users.length);

            DataSet user = users [0];
            assertEquals("[id, name, password]", Arrays.toString(user.getNames()));
            assertEquals("[1, Stiven, pass]", Arrays.toString(user.getValues()));

        }
    }
