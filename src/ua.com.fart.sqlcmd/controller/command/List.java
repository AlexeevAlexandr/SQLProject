package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

import java.util.Arrays;

public class List implements Command {

    private DatabaseManager manager;
    private View view;


    public List(View view, DatabaseManager manager){
        this.manager = manager;
        this.view = view;
    }
    @Override
    public boolean canProces(String command) {
        return command.equals("list");
    }

    @Override
    public void proces(String command) {
        String [] tebleNames = manager.getTableNames();
        String message = Arrays.toString(tebleNames);
        view.write(message+"\n");
    }
}