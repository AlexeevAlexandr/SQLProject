package ua.com.fart.sqlcmd.controller;

import ua.com.fart.sqlcmd.controller.command.Command;
import ua.com.fart.sqlcmd.controller.command.Exit;
import ua.com.fart.sqlcmd.controller.command.Find;
import ua.com.fart.sqlcmd.controller.command.Help;
import ua.com.fart.sqlcmd.controller.command.List;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

public class MainController {
    private Command[] commands;
    private DatabaseManager manager;
    private View view;
    MainController(View view, DatabaseManager manager){
        this.view = view;
        this.manager = manager;
        this.commands = new Command[] {
                new Exit(view),
                new Help(view),
                new List(view, manager),
                new Find(view, manager)};
    }

    public void run(){
        connectToDB();
        while (true) {
            String command = "";
            try {
                view.write("Enter command or 'help' - for help");
                command = view.read();
                if (commands[3].canProces(command)) {//find
                    commands[3].proces(command);
                } else if (commands[2].canProces(command)) {//list
                    commands[2].proces(command);
                } else if (commands[1].canProces(command)) {//help
                    commands[1].proces(command);
                } else if (commands[0].canProces(command)) {//exit
                    commands[0].proces(command);
                } else {
                    view.write("Incorrect command: '" + command + "' try again");
                }
            } catch(Exception e){view.write("Incorrect command:'" + command + "' try again");}
        }
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
