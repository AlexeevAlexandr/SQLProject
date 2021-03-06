package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

import java.util.Set;

public class List implements Command {

    private DatabaseManager manager;
    private View view;

    public List(View view, DatabaseManager manager){
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("list");
    }

    @Override
    public void process(String command) {
        Set<String> tableNames = manager.getTableNames();
        String message = String.valueOf(tableNames);
        view.write("List of tables:");
        view.write(message);
        view.write("");
    }
}
