package ua.com.fart.sqlcmd.test;

import org.junit.Before;
import org.junit.Test;
import ua.com.fart.sqlcmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {

    private static ConfigurableInputStream in;
    private static ByteArrayOutputStream out;

    @Before
    public void setup(){
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();
        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

    @Test
    public void testHelp(){
        in.add("help");
        in.add("exit");
        Main.main(new String[0]);
        assertEquals("Hi user!\n" +
                "Enter please username, password and databaseName.\n" +
                "format have to be: connect,userName,password,databaseName\n" +
                "Available commands:\n" +
                "---------------------\n" +
                "connect: connection to the database, enter: connect,userName,password,dataBaseName\n" +
                "\n" +
                "list: to getting list of tables from base, what you did connect.\n" +
                "\n" +
                "find: to getting contents of your table, enter: find,tableName. tableName it's name of table, what you looking for. \n" +
                "\n" +
                "exit: to exit.\n" +
                "---------------------\n" +
                "Enter command or 'help' - to help\n" +
                "Good by, see you soon.", getData());
    }

    @Test
    public void testExit() {
        in.add("exit");

        Main.main(new String[0]);

        System.out.println((("Hi user!\n" +
                "Enter please username, password and databaseName.\n" +
                "format have to be: connect,userName,password,databaseName\n" +
                "Good by, see you soon.").equals(getData()))? "OK" : "false");
    }


    private String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;
        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
    }
}
