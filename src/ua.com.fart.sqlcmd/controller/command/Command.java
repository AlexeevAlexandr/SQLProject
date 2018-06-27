package ua.com.fart.sqlcmd.controller.command;

public interface Command {

    boolean canProces (String command);

    void proces (String command);
}
