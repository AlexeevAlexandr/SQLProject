package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.model.DataSet;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

public class Create implements Command {


    private final DatabaseManager manager;
    private final View view;

    public Create(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create,");
    }

    @Override
    public void process(String command) {
        try {
            String[] data = command.split("[,]");
            if (data.length % 2 != 0) {
                throw new IllegalArgumentException("There must be even number of parameters");
            }
            String tableName = data[1];
            DataSet dataSet = new DataSet();
            for (int index = 1; index < data.length / 2; index++) {
                String columnName = data[index * 2];
                String columnValue = data[index * 2 + 1];
                dataSet.put(columnName, columnValue);
            }
            manager.create(tableName, dataSet);
            view.write(dataSet.toString() + " recorded in table: " + tableName);
        }catch (Exception e){view.write("Incorrect command, try again");}
    }
}
