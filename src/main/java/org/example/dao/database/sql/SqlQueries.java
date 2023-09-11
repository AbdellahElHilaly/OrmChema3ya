package org.example.dao.database.sql;

import org.example.dao.Helper.SqlHelper;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class SqlQueries {

    abstract public String createEmptyTable(String tableName);

    public String addIntColumnIfNotExists(String tableName, String columnName) {
        return "ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + columnName + " INT";
    }

    public String addDoubleColumnIfNotExists(String tableName, String columnName) {
        return "ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + columnName + " DOUBLE";
    }

    public String addStringColumnIfNotExists(String tableName, String columnName) {
        return "ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + columnName + " VARCHAR(255)";
    }

    public String addBooleanColumnIfNotExists(String tableName, String columnName) {
        return "ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + columnName + " BOOLEAN";
    }

    public String addDateColumnIfNotExists(String tableName, String columnName) {
        return "ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + columnName + " DATE";
    }

    public String addTimeColumnIfNotExists(String tableName, String columnName) {
        return "ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + columnName + " TIME";
    }

    public String addDateTimeColumnIfNotExists(String tableName, String columnName) {
        return "ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + columnName + " DATETIME";
    }

    public String addTextColumnIfNotExists(String tableName, String columnName) {
        return "ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + columnName + " TEXT";
    }

    public String addParagraphColumnIfNotExists(String tableName, String columnName) {
        return "ALTER TABLE " + tableName + " ADD COLUMN IF NOT EXISTS " + columnName + " LONGTEXT";
    }

    public String selectAll(String tableName) {
        return "SELECT * FROM " + tableName + " ORDER BY id ASC";
    }

    public String selectById(String tableName, int id) {
        return "SELECT * FROM " + tableName + " WHERE id = " + id;
    }

    public String deleteById(String tableName, int id) {
        return "DELETE FROM " + tableName + " WHERE id = " + id;
    }

    public String findBy(String tableName, String columnName, String value) {
        return "SELECT * FROM " + tableName + " WHERE " + columnName + " = '" + value + "'";
    }

    public String insertInto(String tableName, String[][] fields, String[] values) {
        String columns = Arrays.stream(fields)
                .skip(1) // Skip the first element (the id field)
                .map(field -> field[1])
                .collect(Collectors.joining(", "));

        String valueList = Arrays.stream(values)
                .skip(1) // Skip the first element (the id value)
                .map(value -> "'" + value + "'")
                .collect(Collectors.joining(", "));

        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, valueList);
    }

    public String update(String tableName, String[][] fields, String[] values, int id) {
        String updateFields = Arrays.stream(fields)
                .filter(field -> !field[1].equals("id")) // Skip the id field
                .map(field -> {
                    String value = SqlHelper.handelBoolenData(values[Arrays.asList(fields).indexOf(field)]);
                    return field[1] + " = '" + value + "'";
                })
                .collect(Collectors.joining(", "));

        return String.format("UPDATE %s SET %s WHERE id = %d", tableName, updateFields, id);
    }


    public String createForeignKey(String firstTableName, String secondTableName) {
        return "ALTER TABLE " + firstTableName +
                " ADD CONSTRAINT FK_" + firstTableName + "_" + secondTableName +
                " FOREIGN KEY (" + secondTableName + "_id) REFERENCES " + secondTableName + "(id)";
    }


    public String updateWhere(String tableName, String[][] fields, String[] values, String where, String value) {
        String updateFields = Arrays.stream(fields)
                .filter(field -> !field[1].equals("id")) // Skip the id field
                .map(field -> {
                    String fieldValue = SqlHelper.handelBoolenData(values[Arrays.asList(fields).indexOf(field)]);
                    return field[1] + " = '" + fieldValue + "'";
                })
                .collect(Collectors.joining(", "));

        return String.format("UPDATE %s SET %s WHERE %s = '%s'", tableName, updateFields, where, value);
    }
}
