package ua.com.fart.sqlcmd.controller;

import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.model.JDBCDatabaseManager;
import ua.com.fart.sqlcmd.view.Console;
import ua.com.fart.sqlcmd.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new Console();
        DatabaseManager manager = new JDBCDatabaseManager();
        MainController controller = new MainController(view, manager);

        controller.run();
    }
}