package org.example.framwork.dao.ORM;

import org.example.framwork.dao.Helper.DaoHelper;
import org.example.framwork.dao.Helper.Factory;

import java.sql.SQLException;

public class RelationalRepository<T> extends CrudOperations<T> {

    public RelationalRepository(Class<T> modelClass) {
        super(modelClass);
    }

    public void attachingTables(String fieldName) throws SQLException {
        String firstTableName = DaoHelper.getTableName(modelClass);
        String secondTableName = DaoHelper.getTableNameFromRelationalField(fieldName);

        this.statement.execute(Factory.getSqlQueries().createForeignKey(firstTableName, secondTableName));

    }



}



