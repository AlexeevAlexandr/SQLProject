package ua.com.fart.sqlcmd.UnitTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import ua.com.fart.sqlcmd.controller.command.Command;
import ua.com.fart.sqlcmd.controller.command.Find;
import ua.com.fart.sqlcmd.model.DataSet;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class FindTest {

    private DatabaseManager manager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        manager = mock(DatabaseManager.class);
        view = mock(View.class);
        command = new Find(view,manager);
    }

    @Test
    public void testCanProcessFindString(){

        boolean canProcess = command.canProcess("find,user");
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessFindCommandIncorrectFirstParameter() {
        boolean canProcess = command.canProcess("fond");
        assertFalse(canProcess);
    }

    @Test
    public void testPrintTableData(){
        when(manager.getTableColumns("user")).thenReturn(new String[] {"id,name.password"});

        DataSet user1 = new DataSet();
        user1.put("id",12);
        user1.put("name","Stevenson");
        user1.put("password","123456");

        DataSet user2 = new DataSet();
        user2.put("id",13);
        user2.put("name","Eva");
        user2.put("password","654321");
        DataSet [] data = new DataSet[] {user1, user2};

        when(manager.getTableData("user")).thenReturn(data);

        command.process("find,user");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(view, atLeastOnce()).write(captor.capture());
        Assert.assertEquals("[------------------, " +
                "|id,name.password|, " +
                "------------------, " +
                "|12|Stevenson|123456|, " +
                "|13|Eva|654321|]", captor.getAllValues().toString());
    }
}
