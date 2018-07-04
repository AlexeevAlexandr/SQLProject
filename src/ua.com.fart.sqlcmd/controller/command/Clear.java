package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

public class Clear implements Command {

    private DatabaseManager manager;
    private View view;

    public Clear(DatabaseManager manager, View view){
        this.manager = manager;
        this.view = view;
    }
    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear");
    }

    @Override
    public void process(String command) {
        try {
            String[] data = command.split("[,]");
            if (data.length != 2) {
                throw new IllegalArgumentException("Format of command have to be: clear,tableName");
            }
            manager.clear(data[1]);
            view.write("Table '" + data[1] + "' was cleared");
        }catch(Exception e){view.write("Incorrect command, try again");}
    }
}
