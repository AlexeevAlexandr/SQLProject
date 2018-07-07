package ua.com.fart.sqlcmd.IntegrationTests;

import java.io.OutputStream;

public class LogOutputStream extends OutputStream{

    private String log;

    @Override
    public void write(int b) {
        log += String.valueOf((char)b);
    }

    public String getData(){
        return log;
    }
}