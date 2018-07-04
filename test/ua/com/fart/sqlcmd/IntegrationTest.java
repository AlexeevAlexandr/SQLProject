package ua.com.fart.sqlcmd;

import org.junit.Before;
import org.junit.Test;
import ua.com.fart.sqlcmd.controller.Main;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.model.JDBCDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;
    private DatabaseManager databaseManager;

    private String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }

    @Before
    public void setup(){
        databaseManager = new JDBCDatabaseManager();
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testConnect() {
        in.add("connect,root,111111,postgres");
        Main.main(new String[0]);
        assertEquals("Hello!\r\n" +
                "Enter please username, password and databaseName\r\n" +
                "format have to be: connect,userName,password,databaseName\r\n" +
                "Connect is successful\r\n" +
                "Enter command or 'help' - to help",getData().trim());
    }

    @Test
    public void testIncorrectConnect() {
        in.add("connect,root,111111");
        Main.main(new String[0]);
        assertEquals("Hello!\r\n" +
                "Enter please username, password and databaseName\r\n" +
                "format have to be: connect,userName,password,databaseName\r\n" +
                "Connect unsuccessful: incorrect entered number of parameters\r\n" +
                "Try again.\r\n" +
                "Enter command or 'help' - to help",getData().trim());
    }

    @Test
    public void testExit() {
        in.add("exit");
        Main.main(new String[0]);
        assertEquals(("Hello!\r\n" +
                "Enter please username, password and databaseName\r\n" +
                "format have to be: connect,userName,password,databaseName\r\n" +
                "Good by, see you soon."),getData().trim());
    }

    @Test
    public void testFind(){
        in.add("connect,root,111111,postgres");
        in.add("find,user");
        Main.main(new String[0]);
        assertEquals(("Hello!\r\n" +
                "Enter please username, password and databaseName\r\n" +
                "format have to be: connect,userName,password,databaseName\r\n" +
                "Connect is successful\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "------------------\r\n" +
                "|id|name|password|\r\n" +
                "------------------\r\n" +
                "|15|Stiven|fhdhfjfjjfjf|\r\n" +
                "Enter command or 'help' - to help"),getData().trim());
    }

    @Test
    public void testHelp(){
        in.add("help");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals(("Hello!\r\n" +
                "Enter please username, password and databaseName\r\n" +
                "format have to be: connect,userName,password,databaseName\r\n" +
                "Available commands:\r\n" +
                "---------------------\r\n" +
                "connect: connection to the database, enter: connect,userName,password,dataBaseName\r\n" +
                "list: to getting list of tables from base, what you did connect.\r\n" +
                "find: to getting contents of your table, enter: find,tableName. tableName it's name of table, what you looking for.\r\n" +
                "exit: to exit.\r\n" +
                "---------------------\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "Good by, see you soon."),getData().trim());
    }

    @Test
    public void testList(){
        in.add("connect,root,111111,postgres");
        in.add("list");
        Main.main(new String[0]);
        assertEquals(("Hello!\r\n" +
                "Enter please username, password and databaseName\r\n" +
                "format have to be: connect,userName,password,databaseName\r\n" +
                "Connect is successful\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "\r\n" +
                "[user, user2]\r\n"+
                "\r\n" +
                "Enter command or 'help' - to help"),getData().trim());
    }

    @Test
    public void testUnsupportedTillConnect() {
        in.add("command");
        Main.main(new String[0]);
        assertEquals(("Hello!\r\n" +
                "Enter please username, password and databaseName\r\n" +
                "format have to be: connect,userName,password,databaseName\r\n" +
                "You can't use any commands while not connected to database with command: connect,userName,password,databaseName\r\n" +
                "Enter command or 'help' - to help"), getData().trim());
    }

    @Test
    public void testUnsupportedAfterConnect() {
        in.add("connect,root,111111,postgres");
        in.add("command");
        Main.main(new String[0]);
        assertEquals(("Hello!\r\n" +
                "Enter please username, password and databaseName\r\n" +
                "format have to be: connect,userName,password,databaseName\r\n" +
                "Connect is successful\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "Incorrect command: 'command' try again\r\n" +
                "Enter command or 'help' - to help"), getData().trim());
    }
}
