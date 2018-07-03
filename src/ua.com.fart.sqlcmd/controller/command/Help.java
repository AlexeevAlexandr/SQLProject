package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.view.View;

public class Help implements Command{

    private View view;

    public Help(View view){
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equals("help");
    }

    @Override
    public void process(String command) {
        view.write("Available commands:");
        view.write("---------------------");
        view.write("connect: connection to the database, enter: connect,userName,password,dataBaseName\n");
        view.write("list: to getting list of tables from base, what you did connect.\n");
        view.write("find: to getting contents of your table, enter: find,tableName. tableName it's name of table, what you looking for. \n");
        view.write("exit: to exit.");
        view.write("---------------------");
    }
}
