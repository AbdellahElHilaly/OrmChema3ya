package org.example.dao.Helper;


import org.example.dao.Type.Paragraph;
import org.example.dao.Type.Text;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.example.dao.Helper.SqlHelper.handelBoolenData;

public class DaoHelper {
    private static String className;


    public static String[][] getClassFields(Class<?> modelClass) {
        Field[] fields = modelClass.getDeclaredFields();
        List<String[]> filteredFields = new ArrayList<>();

        for (Field field : fields) {
            if (!field.isSynthetic()) {
                String fieldType = field.getType().getSimpleName();
                String fieldName = field.getName();
                filteredFields.add(new String[]{fieldType, fieldName});
            }
        }

        return filteredFields.toArray(new String[0][0]);
    }

    public static String getTableName(Class<?> modelClass) {

        String[] words = modelClass.getSimpleName().split("(?=\\p{Upper})");
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words) {
            stringBuilder.append(word.toLowerCase()).append("_");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        return stringBuilder.toString();

    }

    public static String getAddColumnQuery(String tableName, String dataType, String ColumnName) {
        switch (dataType) {
            case "String":
                return Factory.getSqlQueries().addStringColumnIfNotExists(tableName, ColumnName);
            case "int":
                return Factory.getSqlQueries().addIntColumnIfNotExists(tableName, ColumnName);
            case "double":
                return Factory.getSqlQueries().addDoubleColumnIfNotExists(tableName, ColumnName);
            case "boolean":
                return Factory.getSqlQueries().addBooleanColumnIfNotExists(tableName, ColumnName);
            case "Date":
                return Factory.getSqlQueries().addDateColumnIfNotExists(tableName, ColumnName);
            case "Time":
                return Factory.getSqlQueries().addTimeColumnIfNotExists(tableName, ColumnName);
            case "DateTime":
                return Factory.getSqlQueries().addDateTimeColumnIfNotExists(tableName, ColumnName);
            case "Text":
                return Factory.getSqlQueries().addTextColumnIfNotExists(tableName, ColumnName);
            case "Paragraph":
                return Factory.getSqlQueries().addParagraphColumnIfNotExists(tableName, ColumnName);
            default:
                return Factory.getSqlQueries().addStringColumnIfNotExists(tableName, ColumnName);
        }
    }

    public static <T> String[] getClassValues(T model) {
        Field[] fields = model.getClass().getDeclaredFields();
        List<String> values = new ArrayList<>();

        for (Field field : fields) {
            if (!field.isSynthetic()) {
                field.setAccessible(true);
                try {
                    values.add(String.valueOf(field.get(model)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return values.toArray(new String[0]);
    }

    public static <T> void setFieldByName(T instance, String fieldName, Object value) throws IllegalAccessException {
        try {

            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            value = handleNullableValue(instance, field, value);

            if (isSpecialFieldType(field.getType())) {
                handleSpecialField(instance, field, value);
            }
            else {
                field.set(instance, value);
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isSpecialFieldType(Class<?> fieldType) {
        return fieldType.equals(Text.class) || fieldType.equals(Paragraph.class);
    }

    private static <T> void handleSpecialField(T instance, Field field, Object value) throws IllegalAccessException {
        if (value instanceof String) {
            Object specialValue = Factory.createSpecialFieldValue(field.getType(), (String) value);
            field.set(instance, specialValue);
            specialValue = null;
        } else {
            throw new IllegalArgumentException("Invalid value type for special field: " + field.getName());
        }
    }


    private static <T> Object handleNullableValue(T instance, Field field, Object value) {
        if (value == null) {
            Class<?> fieldType = field.getType();
            if (fieldType.equals(boolean.class)) {
                value = false;
            } else if (fieldType.equals(double.class)) {
                value = 0.0;
            } else if (fieldType.equals(Date.class)) {
                value = new Date();
            } else if (fieldType.equals(int.class)) {
                value = 0;
            } else if (fieldType.equals(String.class)) {
                value = "";
            } else if (fieldType.equals(Paragraph.class)) {
                value = "";
            } else if (fieldType.equals(Text.class)) {
                value = "";
            } else {
                try {
                    value = fieldType.getDeclaredConstructor().newInstance();
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                         InvocationTargetException e) {
                    throw new RuntimeException("Error handling nullable value: " + e.getMessage());
                }
            }
            System.out.println(value);
            fieldType = null;
        }
        return value;
    }

    public static boolean isRelationalField(String fieldName) {
        return fieldName.contains("_id");
    }


    public static String getTableNameFromRelationalField(String fieldName) {
        return fieldName.substring(0, fieldName.length() - 3);
    }

    public static void convertToSqlValues(String[][] fields, String[] values) {

        for (int i = 0; i < fields.length; i++) {
            if (fields[i][0].equals("Date")) {
                values[i] = SqlHelper.handelDateData(values[i]);
            }
            if (fields[i][0].equals("boolean")) {
                values[i] = handelBoolenData(values[i]);
            }
        }
    }

    public static int findColumnIndex(ResultSetMetaData rsmd, String fieldName) throws SQLException {
        int columnsNumber = rsmd.getColumnCount();
        for (int i = 1; i <= columnsNumber; i++) {
            String columnName = rsmd.getColumnName(i);
            if (columnName.equalsIgnoreCase(fieldName)) {
                return i; // Found a matching column name
            }
        }
        return -1; // No matching column name found
    }
}

