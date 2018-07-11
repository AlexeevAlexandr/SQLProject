package ua.com.fart.sqlcmd.UnitTestsWithMockito;

import org.junit.Before;
import org.junit.Test;
import ua.com.fart.sqlcmd.controller.command.Command;
import ua.com.fart.sqlcmd.controller.command.List;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class ListTest {
    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new List(view,manager);
    }

    @Test
    public void testCanProcessListCommandTrue(){

        boolean canProcess = command.canProcess("list");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessListCommandFalse() {
        boolean canProcess = command.canProcess("ist");
        assertFalse(canProcess);
    }


}

