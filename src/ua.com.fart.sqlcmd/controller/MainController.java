package ua.com.fart.sqlcmd.controller;

import ua.com.fart.sqlcmd.controller.command.Command;
import ua.com.fart.sqlcmd.controller.command.Connect;
import ua.com.fart.sqlcmd.controller.command.Exit;
import ua.com.fart.sqlcmd.controller.command.Find;
import ua.com.fart.sqlcmd.controller.command.Help;
import ua.com.fart.sqlcmd.controller.command.List;
import ua.com.fart.sqlcmd.controller.command.Unsupported;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

public class MainController {
    private Command[] commands;
    private View view;

    MainController(View view, DatabaseManager manager){
        this.view = view;
        this.commands = new Command[] {
                new Connect(view, manager),
                new Exit(view),
                new Help(view),
                new List(view, manager),
                new Find(view, manager),
                new Unsupported(view)};
    }
    public void run(){
        view.write("Hi user!");
        view.write("Enter please username, password and databaseName.\n" +
                "format have to be: connect,userName,password,databaseName");
        while (true) {
            try {
                String input = view.read();
                for (Command command : commands) {
                    if (command.canProces(input)) {
                        command.proces(input);
                        break;
                    }
                }
                view.write("Enter command or 'help' - for help");
            } catch(Exception e){view.write("Incorrect command try again");}
        }
    }
}
