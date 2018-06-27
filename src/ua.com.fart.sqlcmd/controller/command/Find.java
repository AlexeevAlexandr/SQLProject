package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.model.DataSet;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

public class Find implements Command {

    private View view;
    private DatabaseManager manager;

    public Find(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    private void printHeader(String[] tableColumns) {
        StringBuilder result = new StringBuilder("|");
        for (String name : tableColumns) {
            result.append(name).append("|");
        }
        view.write("------------------");
        view.write(result.toString());
        view.write("------------------");
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

    @Override
    public boolean canProces(String command) {
        return command.startsWith("find");
    }

    @Override
    public void proces(String command) {
        String[] data = command.split("[,]");
        String tableName = data[1];

        DataSet[] tableData = manager.getTableData(tableName);
        String[] tableColumns = manager.getTableColumns(tableName);
        printHeader(tableColumns);
        printTable(tableData);
    }
}
