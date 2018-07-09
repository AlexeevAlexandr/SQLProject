package ua.com.fart.sqlcmd.UnitTestsWithMockito;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.fart.sqlcmd.controller.command.Command;
import ua.com.fart.sqlcmd.controller.command.Exit;
import ua.com.fart.sqlcmd.controller.command.ExitException;
import ua.com.fart.sqlcmd.view.View;

import static org.junit.Assert.*;

public class ExitTest {
    private View view = Mockito.mock(View.class);
    private Command command;

    @Before
    public void setup() {
        command = new Exit(view);
    }

    @Test
    public void testCanProcessExitString(){
        boolean canProcess = command.canProcess("exit");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessExitCommand(){
        boolean canProcess = command.canProcess("ewre");
        assertFalse(canProcess);
    }

    @Test
    public void testCanProcessExitCommand_throwsExitException(){
        try {
            command.process("exit");
            fail("Expected ExitException");
        }catch (ExitException ignored){}
        Mockito.verify(view).write("Good by, see you soon.");
    }


}
