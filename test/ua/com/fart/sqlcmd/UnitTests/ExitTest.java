package ua.com.fart.sqlcmd.UnitTests;

import org.junit.Assert;
import org.junit.Test;
import ua.com.fart.sqlcmd.controller.command.Command;
import ua.com.fart.sqlcmd.controller.command.Exit;
import ua.com.fart.sqlcmd.controller.command.ExitException;

import static org.junit.Assert.*;

public class ExitTest {
    private TestView view = new TestView();

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
        Assert.assertEquals("Good by, see you soon.",view.getContent().trim());
    }


}
