package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

public class Connect implements Command {
    private View view;
    private DatabaseManager manager;

    public Connect(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProces(String command) {
        return command.startsWith("connect");
    }

    @Override
    public void proces(String command) {
        try {
            String [] data = command.split("[,]");
            if(data.length != 4){
                throw new IllegalArgumentException("Not correct entered number of parameters");
            }
            String userName = data[1];
            String password = data[2];
            String databaseName = data[3];
            manager.connect(userName, password, databaseName);
            view.write("Connect is successful");
        } catch (Exception e) {printError(e);}
    }
    private void printError(Exception e) {
        String message = (e.getCause() != null) ? e.getMessage() + " " + e.getCause().getMessage() : e.getMessage();
        view.write("Connect isn't successful:\n" + message + "\n" + "Try again.");
    }
}
