package org.example.dao.ORM;

import org.example.dao.Helper.DaoHelper;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class ModelMapper<T> {

    private T instance;
    private final List<T> dataList = new ArrayList<>();

//    public T mapData(ResultSet resultSet) {
//
//        if(resultSet == null) return null;
//        try {
//            instance = createInstance();
//
//            ResultSetMetaData rsmd = null;
//
//            rsmd = resultSet.getMetaData();
//            int columnsNumber = rsmd.getColumnCount();
//
//            for (int i = 1; i <= columnsNumber; i++) {
//                String columnName = rsmd.getColumnName(i);
//                Object columnValue = resultSet.getObject(i);
//
//                DaoHelper.setFieldByName(instance, columnName, columnValue); //pass instance by reference
//                columnName = null;
//                columnValue = null; //free memory
//            }
//            rsmd = null;
//
//            return instance;
//
//
//        } catch (SQLException | IllegalAccessException e) {
//            throw new RuntimeException(e);
//        }
//
//    }


    public T mapData(ResultSet resultSet) {
        if (resultSet == null) return null;
        try {
            instance = createInstance();

            ResultSetMetaData rsmd = resultSet.getMetaData();

            // Get the fields of the instance's class
            Field[] fields = instance.getClass().getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();

                // Find the corresponding column index based on the field name
                int columnIndex = DaoHelper.findColumnIndex(rsmd, fieldName);

                if (columnIndex != -1) {
                    Object columnValue = resultSet.getObject(columnIndex);

                    // Set the field's value in the instance
                    DaoHelper.setFieldByName(instance, fieldName, columnValue);
                }
            }

            rsmd = null;
            return instance;
        } catch (SQLException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }






    public List<T> mapDataList(ResultSet resultSet) {
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            instance = createInstance();
            instance = mapData(resultSet);
            this.dataList.add(instance);
        }
        return this.dataList;
    }

    public abstract T createInstance();


    public void initializeObject() {

        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                if (field.getType().equals(int.class)) {
                    field.set(this, 0);
                } else if (field.getType().equals(boolean.class)) {
                    field.set(this, false);
                } else if (field.getType().equals(double.class)) {
                    field.set(this, 0);
                } else if (field.getType().equals(float.class)) {
                    field.set(this, 0);
                } else if (field.getType().equals(char.class)) {
                    field.set(this, ' ');
                } else if (field.getType().equals(byte.class)) {
                    field.set(this, (byte) 0);
                } else if (field.getType().equals(String.class)) {
                    field.set(this, null);
                } else if (field.getType().equals(Date.class)) {
                    field.set(this, new Date(0));
                } else if (field.getType().equals(Timestamp.class)) {
                    field.set(this, new Timestamp(0));
                } else if (field.getType().equals(Time.class)) {
                    field.set(this, new Time(0));
                } else {
                    field.set(this, null);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }





}
