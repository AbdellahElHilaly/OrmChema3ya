package org.example.framwork.dao.ORM;

import org.example.framwork.dao.Helper.Factory;
import org.example.framwork.dao.Helper.PrintHelper;
import org.example.framwork.dao.Helper.DaoHelper;
import org.example.framwork.dao.utils.config.AppConfig;
import org.example.framwork.dao.utils.enums.RunMood;

import java.sql.SQLException;

public  class BaseRepository<T> extends RelationalRepository<T> {

    public BaseRepository(Class<T> modelClass) {
        super(modelClass);
        try {
            if(AppConfig.APP_MOOD.equals(RunMood.Development.name())){
                this.createTable();
                this.addColumns();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void createTable() throws SQLException {

        System.out.println();
        PrintHelper.printWarningMessage("Creating table " + tableName + "...................................");
        System.out.println();

        this.statement.execute(Factory.getSqlQueries().createEmptyTable(tableName));

        PrintHelper.printSuccessMessage("Table " + tableName + " created successfully");
    }

    public void addColumns() throws SQLException {
        String[][] fields = DaoHelper.getClassFields(modelClass);
        for (String[] field : fields) {
            PrintHelper.printInfoMessage("Adding column " + field[1] + " to table " + tableName + "...................................");
            this.statement.execute(DaoHelper.getAddColumnQuery(tableName, field[0], field[1]));
            PrintHelper.printSuccessMessage("Column " + field[1] + " added successfully");

            if (DaoHelper.isRelationalField(field[1])) {
                PrintHelper.printInfoMessage("Attaching table " + field[1] + " to table " + tableName + "...................................");
                this.attachingTables(field[1]);
                PrintHelper.printSuccessMessage("Table " + field[1] + " attached successfully");
            }
        }



        fields = null;

    }


}