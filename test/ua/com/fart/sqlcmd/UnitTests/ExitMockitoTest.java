package ua.com.fart.sqlcmd.UnitTests;

import org.junit.Test;
import org.mockito.Mockito;
import ua.com.fart.sqlcmd.controller.command.Command;
import ua.com.fart.sqlcmd.controller.command.Exit;
import ua.com.fart.sqlcmd.controller.command.ExitException;
import ua.com.fart.sqlcmd.view.View;

import static org.junit.Assert.*;

public class ExitMockitoTest {
    private View view = Mockito.mock(View.class);

    @Test
    public void testCanProcessExitString(){
        Command command = new Exit(view);
        boolean canProcess = command.canProcess("exit");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessExitCommand(){
        Command command = new Exit(view);
        boolean canProcess = command.canProcess("ewre");
        assertFalse(canProcess);
    }

    @Test
    public void testCanProcessExitCommand_throwsExitException(){
        Command command = new Exit(view);
        try {
            command.process("exit");
            fail("Expected ExitException");
        }catch (ExitException ignored){}
        Mockito.verify(view).write("Good by, see you soon.");
    }


}
