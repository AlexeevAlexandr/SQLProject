package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.view.View;

public class Unsupported implements Command {
    private View view;

    public Unsupported(View view) {
        this.view = view;
    }

    @Override
    public boolean canProces(String command) {
        return true;
    }

    @Override
    public void proces(String command) {
        view.write("Incorrect command: '" + command + "' try again");
    }
}
