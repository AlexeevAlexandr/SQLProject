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
        view.write("connect: connection to the database, enter: connect,userName,password,dataBaseName");
        view.write("list: to getting list of tables from base, what you did connect");
        view.write("clear: to clear all data from table, format query have to be: clear,tableName");//TODO not safe command, will make check before clear
        view.write("create: to create data in table, format query have to be: create,tableName,nameColumn1,value,nameColumn2,value,nameColumn3,value.......nameColumnN,value");
        view.write("find: to getting contents from table, format query have to be: find,tableName. tableName it's name of table, what you looking for");
        view.write("exit: to exit");
        view.write("---------------------");
    }
}
