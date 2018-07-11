package ua.com.fart.sqlcmd.UnitTestsWithMockito;

import org.junit.Before;
import org.junit.Test;
import ua.com.fart.sqlcmd.controller.command.Command;
import ua.com.fart.sqlcmd.controller.command.Connect;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConnectTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Connect(view, manager);
    }

    @Test
    public void testCanProcessConnectCommandTrue(){

        boolean canProcess = command.canProcess("connect,");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessConnectCommandFalse() {
        boolean canProcess = command.canProcess("conect");
        assertFalse(canProcess);
    }

//    @Test
//    public void testConnectTrue(){
//        command.process("connect,root,111111,postgres");
//        try {
//            verify(view).write("Connect is successful");
//        }catch (Exception ignored){}
//    }

    @Test
    public void testConnectIncorrectEnteredData(){
        command.process("connect,rot,111111,postgres");
        verify(view).write("Connect unsuccessful: error connection: username, password or database name not found, check entering data");
    }

    @Test
    public void testConnectIncorrectEnteredNumberParameters(){
        command.process("connect,root,111111");
        verify(view).write("Connect unsuccessful: incorrect entered number of parameters");
    }
}
