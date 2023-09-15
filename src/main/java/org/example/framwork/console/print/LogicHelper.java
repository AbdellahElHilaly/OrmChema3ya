package org.example.framwork.console.print;

import org.example.framwork.dao.Helper.PrintHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogicHelper {

    private static Date tempDate;
    private static final SimpleDateFormat inputFormatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
    private static final SimpleDateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat dateTileFormat = new SimpleDateFormat("yyyy-MM-dd (HH-mm-ss)");


    public static String dateToString(Date date) {
        return outputFormatter.format(date);
    }

    public static Date stringToDate(String date) {
        try {
            return inputFormatter.parse(date);
        } catch (ParseException e) {
            PrintHelper.printInfoMessage("Use this format: yyyy-MM-dd");
            throw new RuntimeException(e);
        }
    }


    public static Date generateReturnDate(int tempBorrowingDays) {
        tempDate = null;
        tempDate = new Date();
        tempDate.setTime(tempDate.getTime() + ((long) tempBorrowingDays * 24 * 60 * 60 * 1000));

        return (tempDate);
    }


    public static String formatDateTime(Date date) {
        return dateTileFormat.format(date);
    }

    public static String[] handelHeaderToPrint(String[] modelHeader) {
        modelHeader[0] = "index";
        return modelHeader;
    }

    public static String[] handelBodyToPrint(String[] modelBody, int i) {
        modelBody[0] = String.valueOf(i);
        return modelBody;
    }
}


