package ua.com.fart.sqlcmd.controller;

import ua.com.fart.sqlcmd.controller.command.*;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

public class MainController {
    private Command[] commands;
    private View view;

    MainController(View view, DatabaseManager manager){
        this.view = view;
        this.commands = new Command[] {
                new Connect(view, manager),
                new Help(view),
                new Exit(view),
                new IsConnected(manager, view),
                new Create (manager, view),
                new Clear(manager, view),
                new List(view, manager),
                new Find(view, manager),
                new Unsupported(view)};
    }

    public void run(){
        try {
            doWork();
        } catch(Exception e){/*do nothing*/}
    }

    private void doWork() {
        view.write("Hello!");
        view.write("Enter please username, password and databaseName");
        view.write("format have to be: connect,userName,password,databaseName");

        while (true) {
            String input = view.read();
            for (Command command : commands) {
                try {
                    if (command.canProcess(input)) {
                        command.process(input);
                        break;
                    }
                }catch (Exception e) {
                    if (e instanceof ExitException) {
                        throw e;
                    }
                }
            }
            view.write("Enter command or 'help' - to help");
        }

    }
}
