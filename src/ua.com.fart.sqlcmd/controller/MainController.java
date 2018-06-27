package ua.com.fart.sqlcmd.controller;

import ua.com.fart.sqlcmd.controller.command.Command;
import ua.com.fart.sqlcmd.controller.command.Exit;
import ua.com.fart.sqlcmd.controller.command.Help;
import ua.com.fart.sqlcmd.controller.command.List;
import ua.com.fart.sqlcmd.model.DataSet;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

public class MainController {
    private Command[] commands;
    private DatabaseManager manager;
    private View view;
    MainController(View view, DatabaseManager manager){
        this.view = view;
        this.manager = manager;
        this.commands = new Command[] {new Exit(view), new Help(view), new List(manager, view)};
    }

    public void run(){
        connectToDB();
        while (true) {
            try {
                view.write("Enter command or 'help' - for help");
                String command = view.read();
                if (command.startsWith("find")) {
                    doFind(command);
                } else if (commands[2].canProces(command)) {//list
                    commands[2].proces(command);
                } else if (commands[1].canProces(command)) {//help
                    commands[1].proces(command);
                } else if (commands[0].canProces(command)) {//exit
                    commands[0].proces(command);
                } else {
                    view.write("Incorrect command: '" + command + "' try again");
                }
            } catch(Exception e){view.write("Incorrect command, try again");}
        }
    }

    private void doFind(String command) {
            String[] data = command.split("[,]");
            String tableName = data[1];

            DataSet[] tableData = manager.getTableData(tableName);
            String[] tableColumns = manager.getTableColumns(tableName);
            printHeader(tableColumns);
            printTable(tableData);
    }

    private void printTable(DataSet[] tableData) {
        for(DataSet row : tableData){
            printRow(row);
        }
    }

    private void printRow(DataSet row) {
        Object [] result = row.getValues();
        String rowString = "|";
        for (Object value : result) {
            rowString += value + "|";
        }
        view.write(rowString);
    }

    private void printHeader(String[] tableColumns) {
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.write("------------------");
        view.write(result);
        view.write("------------------");
    }

    private void connectToDB() {
        view.write("Hi user!");
        while (true) {
            view.write("Enter please username, password and databaseName.\n" +
                    "format have to be: userName,password,databaseName");
            try {
            String string = view.read();
            String[] data = string.split("[,]");
            if(data.length != 3){
                throw new IllegalArgumentException("\nNot correct entered number of parameters");
            }
            String userName = data[0];
            String password = data[1];
            String databaseName = data[2];

            manager.connect(userName, password, databaseName);
                    break;
            } catch (Exception e) {
                printError(e);
            }
        }
        view.write("Connect is successful");
    }

    private void printError(Exception e) {
        String message = (e.getCause() != null) ? e.getMessage() + " " + e.getCause().getMessage() : e.getMessage();
        view.write("Connect isn't successful:\n" + message + "\n" + "Try again.");
    }
}
