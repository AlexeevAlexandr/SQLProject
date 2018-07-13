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
        view.write("=========================================================================================");
        view.write("Available commands:");
        view.write("=========================================================================================");
        view.write("connect: connection to the database, enter: connect,userName,password,dataBaseName");
        view.write("-----------------------------------------------------------------------------------------");
        view.write("list:    getting list of tables from base, what you did connect");
        view.write("-----------------------------------------------------------------------------------------");
        view.write("clear:   clear all data from table, format query have to be: clear,tableName");
        view.write("-----------------------------------------------------------------------------------------");
        view.write("create:  create data in table, format query have to be: ");
        view.write("create,tableName,nameColumn1,value1,nameColumn2,value2,nameColumn3,value3...nameColumnN,valueN");
        view.write("-----------------------------------------------------------------------------------------");
        view.write("find:    getting contents from table, format query have to be: find,tableName.");
        view.write("tableName it's name of table, what you are looking for");
        view.write("-----------------------------------------------------------------------------------------");
        view.write("exit:    to exit");
        view.write("=========================================================================================");
    }
}
