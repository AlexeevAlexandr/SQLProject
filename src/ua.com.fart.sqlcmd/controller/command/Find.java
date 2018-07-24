package ua.com.fart.sqlcmd.controller.command;

import ua.com.fart.sqlcmd.model.DataSet;
import ua.com.fart.sqlcmd.model.DatabaseManager;
import ua.com.fart.sqlcmd.view.View;

import java.util.List;

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
            result.append(String.format("%-10s",name)).append("|");
        }
        view.write("==================================");
        view.write(result.toString());
        view.write("==================================");
    }

    private void printTable(DataSet[] tableData) {
        for(DataSet row : tableData){
            printRow(row);
        }
    }

    private void printRow(DataSet row) {
        List<Object> result = row.getValues();
        StringBuilder rowString = new StringBuilder("|");
        for (Object value : result) {
            rowString.append(String.format("%-10s",value)).append("|");
        }
        view.write(rowString.toString());
        view.write("----------------------------------");
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("find");
    }

    @Override
    public void process(String command) {
        try
        {
            String[] data = command.split("[,]");
            String tableName = data[1];
            DataSet[] tableData = manager.getTableData(tableName);
            String[] tableColumns = manager.getTableColumns(tableName);
            printHeader(tableColumns);
            printTable(tableData);
        }catch (Exception e){view.write("Incorrect command, try again.");}
    }
}
