package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.view.View;

public class Help implements Command{

    private View view;

    public Help(View view){
        this.view = view;
    }

    @Override
    public boolean canProces(String command) {
        return command.equals("help");
    }

    @Override
    public void proces(String command) {
        view.write("Available commands:\t");
        view.write("---------------------");
        view.write("list: \n \t for getting list of tables from base, what you did connect.\n");
        view.write("find,tableName: \n \t " +
                "for getting contents of your table, type 'find,tableName'. " +
                "tableName it's name of table, what you looking for. \n");
        view.write("exit: \n \t for exit.");
        view.write("---------------------");
    }
}
