package ua.com.fart.sqlcmd;

import org.junit.Before;
import org.junit.Test;
import ua.com.fart.sqlcmd.model.DataSet;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.model.JDBCDatabaseManager;

import java.util.Arrays;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;

public class JDBCDatabaseManagerTest {
    private DatabaseManager manager;

        @Before
        public void setup () {
            manager = new JDBCDatabaseManager();
            manager.connect("root", "111111", "postgres");
        }

        @Test
        public void testGetAllTableNames(){
            Set<String> tableNames = manager.getTableNames();
            assertEquals("[user2, user]", tableNames.toString());
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
            manager.create("user", input);

            DataSet[] users = manager.getTableData("user");
            DataSet user = users [0];
            assertEquals("[id, name, password]", user.getNames().toString());
            assertEquals("[1, Stiven, pass]", user.getValues().toString());

        }

        @Test
        public void testUpdateTableData() {

        manager.clear("user");

        DataSet input = new DataSet();
        input.put("id", 13);
        input.put("name", "Stiven");
        input.put("password", "pass");
        manager.create("user", input);

        DataSet newValue = new DataSet();
        newValue.put("name", "Pup");
        newValue.put("password", "pass2");
        manager.update("user", 13, newValue);

        DataSet[] users = manager.getTableData("user");
        assertEquals(1, users.length);

        DataSet user = users[0];
        assertEquals("[id, name, password]", user.getNames().toString());
        assertEquals("[13, Pup, pass2]", user.getValues().toString());
    }

        @Test
        public void testGetColumnNames() {
        manager.clear("user");
        String[] columnNames = manager.getTableColumns("user");
        assertEquals("[id, name, password]", Arrays.toString(columnNames));
    }
}