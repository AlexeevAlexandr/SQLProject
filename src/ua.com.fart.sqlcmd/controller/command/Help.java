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
        view.write("Available commands:");
        view.write("---------------------");
        view.write("connect: for connection to the database, enter: connect,userName,password, dataBaseName\n");
        view.write("list: for getting list of tables from base, what you did connect.\n");
        view.write("find: for getting contents of your table, enter: find,tableName. tableName it's name of table, what you looking for. \n");
        view.write("exit: for exit.");
        view.write("---------------------");
    }
}
