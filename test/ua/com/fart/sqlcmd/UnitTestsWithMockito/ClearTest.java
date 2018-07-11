package ua.com.fart.sqlcmd.UnitTestsWithMockito;

import org.junit.Before;
import org.junit.Test;
import ua.com.fart.sqlcmd.controller.command.Clear;
import ua.com.fart.sqlcmd.controller.command.Command;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class ClearTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Clear(manager,view);
    }

    @Test
    public void testCanProcessClearCommandTrue(){

        boolean canProcess = command.canProcess("clear,");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessClearCommandFalse() {
        boolean canProcess = command.canProcess("lear");
        assertFalse(canProcess);
    }

//    @Test
//    public void testClearTableData(){
//        command.process("clear,user");
//        verify(manager).clear("user");
//        verify(view).write("Table 'user' was cleared");
//    }

    @Test
    public void testClearTableData2(){
        try{
            command.process("clear");
            fail();
        }catch(IllegalArgumentException e){
        assertEquals("Format of command have to be: clear,tableName", e.getMessage());
        }
    }

    @Test
    public void testClearTableData3(){
        try{
            command.process("clear,data,data");
            fail();
        }catch(IllegalArgumentException e){
        assertEquals("Format of command have to be: clear,tableName", e.getMessage());
        }
    }
}
