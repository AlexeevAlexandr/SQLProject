package ua.com.fart.sqlcmd.UnitTests;

import ua.com.fart.sqlcmd.view.View;

public class TestView implements View {

    private String messages = "";

    @Override
    public void write(String message) {
        messages += message + "\n";
    }

    @Override
    public String read() {
        return null;
    }

    public String getContent() {
        return messages;
    }
}
