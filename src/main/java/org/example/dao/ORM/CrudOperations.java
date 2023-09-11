package org.example.dao.ORM;

import org.example.dao.Helper.DaoHelper;
import org.example.dao.Helper.Factory;
import org.example.dao.Helper.Printer;
import org.example.dao.database.connection.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CrudOperations<T> {

    protected final Class<T> modelClass;
    protected final String tableName;
    protected java.sql.Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;

    public CrudOperations(Class<T> modelClass) {
        this.modelClass = modelClass;
        this.tableName = DaoHelper.getTableName(modelClass);
        try {
            this.connection = Connection.getConnection();
            this.statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet find(int id) {
        return this.findOrThrow(id);
    }

    public ResultSet findAll() {
        try {
            return this.statement.executeQuery(Factory.getSqlQueries().selectAll(tableName));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  ResultSet findBy(String columnName, String value){
        try {
            resultSet = this.statement.executeQuery(Factory.getSqlQueries().findBy(tableName,columnName,value));
            if (!resultSet.next()) return null;
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void delete(int id) {
        try {
            this.findOrThrow(id);
            this.statement.execute(Factory.getSqlQueries().deleteById(tableName, id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public ResultSet save(T model) {

        try {
            String[][] fields = DaoHelper.getClassFields(modelClass);
            String[] values = DaoHelper.getClassValues(model);

            DaoHelper.convertToSqlValues(fields, values);

            String query = Factory.getSqlQueries().insertInto(tableName, fields, values);
            this.statement.execute(query, Statement.RETURN_GENERATED_KEYS);

            resultSet = statement.getGeneratedKeys();

            resultSet.next();
            int generatedId = resultSet.getInt(1);


            resultSet.close();
            fields = null;
            values = null;
            query = null;

            return this.findOrThrow(generatedId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ResultSet update(T model, int id) {
        try {
            this.findOrThrow(id);
            String[][] fields = DaoHelper.getClassFields(modelClass);
            String[] values = DaoHelper.getClassValues(model);
            String query = Factory.getSqlQueries().update(tableName, fields, values, id);
            this.statement.execute(query);
            fields = null;
            values = null;
            query = null;
            return this.findOrThrow(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private ResultSet findOrThrow(int id) {
        try {
            resultSet = this.statement.executeQuery(Factory.getSqlQueries().selectById(tableName, id));
            if (!resultSet.next()) Printer.error("no " + tableName + " with id " + id + " found");
            return resultSet;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet updateWhere(String where, String value, T model) {
        try {
            String[][] fields = DaoHelper.getClassFields(modelClass);
            String[] values = DaoHelper.getClassValues(model);
            String query = Factory.getSqlQueries().updateWhere(tableName, fields, values, where, value);
            this.statement.execute(query);
            fields = null;
            values = null;
            query = null;
            return this.findBy(where, value);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}