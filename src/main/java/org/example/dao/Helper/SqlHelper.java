package org.example.dao.Helper;


public class SqlHelper {
    public static String handelBoolenData(String value) {
        if (value.equals("true")) value = "1";
        if (value.equals("false")) value = "0";
        return value;
    }


    public static String handelDateData(String value) {
        return LogicHelper.dateToString(LogicHelper.stringToDate(value));
    }
}
