package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.view.View;

public class Exit implements Command {

    private View view;

    public Exit (View view){
        this.view = view;
    }

    @Override
    public boolean canProces(String command) {
        return command.equals("exit");
    }

    @Override
    public void proces(String command) {
        view.write("Good by, see soon.");
        System.exit(0);
    }
}
