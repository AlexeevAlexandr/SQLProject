package ua.com.fart.sqlcmd.IntegrationTests;

import org.junit.Before;
import org.junit.Test;
import ua.com.fart.sqlcmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {

    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;

    String getData() {
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
                "Try again\r\n" +
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
                "|13|Stiven|******|\r\n" +
                "|14|Eva|++++++|\r\n" +
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
                "list: to getting list of tables from base, what you did connect\r\n" +
                "clear: to clear all data from table, format query have to be: clear,tableName\r\n" +
                "create: to create data in table, format query have to be: create,tableName,nameColumn1,value,nameColumn2,value,nameColumn3,value.......nameColumnN,value\r\n" +
                "find: to getting contents from table, format query have to be: find,tableName. tableName it's name of table, what you looking for\r\n" +
                "exit: to exit\r\n" +
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

    @Test
    public void testClear() {
        in.add("connect,root,111111,postgres");
        in.add("clear,user");
        in.add("y");
        in.add("create,user,id,13,name,Stiven,password,******");
        in.add("create,user,id,14,name,Eva,password,++++++");
        in.add("find,user");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals(("Hello!\r\n" +
                "Enter please username, password and databaseName\r\n" +
                "format have to be: connect,userName,password,databaseName\r\n" +
                "Connect is successful\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "You try clear table user\r\n"+
                "Confirm deleting data (y - confirm, another key - abort)\r\n"+
                "Table 'user' was cleared\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "{names: [id, name, password], values: [13, Stiven, ******]} recorded in table: user\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "{names: [id, name, password], values: [14, Eva, ++++++]} recorded in table: user\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "------------------\r\n" +
                "|id|name|password|\r\n" +
                "------------------\r\n" +
                "|13|Stiven|******|\r\n" +
                "|14|Eva|++++++|\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "Good by, see you soon."), getData().trim());
    }

    @Test
    public void testClearWithError() {
        in.add("connect,root,111111,postgres");
        in.add("clear,");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals(("Hello!\r\n" +
                "Enter please username, password and databaseName\r\n" +
                "format have to be: connect,userName,password,databaseName\r\n" +
                "Connect is successful\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "Incorrect command: 'clear,' try again\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "Good by, see you soon."), getData().trim());
    }

    @Test
    public void testCreateWithError() {
        in.add("connect,root,111111,postgres");
        in.add("create,");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals(("Hello!\r\n" +
                "Enter please username, password and databaseName\r\n" +
                "format have to be: connect,userName,password,databaseName\r\n" +
                "Connect is successful\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "Incorrect command, try again\r\n" +
                "Enter command or 'help' - to help\r\n" +
                "Good by, see you soon."), getData().trim());
    }
}
