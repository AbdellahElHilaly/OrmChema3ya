package org.example.framwork.dao.Helper;

import org.example.app.shared.Helper.LogicHelper;

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
