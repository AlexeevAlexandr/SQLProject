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
        String[] data = command.split("[,]");
        if (data.length != 2) {
            throw  new IllegalArgumentException("Format of command have to be: clear,tableName");
        }
        confirmClear(data[1]);
    }

    private void confirmClear(String datum) {
        view.write("You try clear table '" + datum + "'");
        view.write("Confirm deleting data (y - confirm, another key - abort)");
        String confirm = view.read();
        if (confirm.equals("y")) {
            manager.clear(datum);
        }else{view.write("operation aborted");}
    }
}
