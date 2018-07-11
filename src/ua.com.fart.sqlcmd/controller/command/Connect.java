package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

public class Connect implements Command {

    private static String COMMAND_SAMPLE = "connect,root,111111,postgres";

    private View view;
    private DatabaseManager manager;

    public Connect(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect");
    }

    @Override
    public void process(String command) {
        try
        {
            String [] data = command.split("[,]");
            if(data.length != count()){
                throw new IllegalArgumentException("incorrect entered number of parameters");
            }
            String userName = data[1];
            String password = data[2];
            String databaseName = data[3];
            manager.connect(userName, password, databaseName);
            if (!manager.isConnected()) {throw new IllegalArgumentException("error connection: " +
                    "username, password or database name not found, check entering data");}
            view.write("Connect is successful");
        } catch (Exception e) {printError(e);}
    }

    private int count() {
        return COMMAND_SAMPLE.split("[,]").length;
    }

    private void printError(Exception e) {
        String message = (e.getCause() != null) ? e.getMessage() + " " + e.getCause().getMessage() : e.getMessage();
        view.write("Connect unsuccessful: "+message);
        view.write("Try again");
    }
}
