package org.example.framwork.dao.Helper;

import java.lang.reflect.Field;
import java.util.List;

public class PrintHelper {

    public static void printSuccessMessage(String success) {
        System.out.println("\u001B[32m::" + success + ": ::\u001B[0m");
    }

    public static void printErrorMessage(String error) {
        System.out.println("\u001B[31m::" + error + ": ::\u001B[0m");
    }
    public static void printWarningMessage(String warning) {
        System.out.println("\u001B[33m::" + warning + ": ::\u001B[0m");
    }

    public static void printInfoMessage(String info) {
        System.out.println("\u001B[34m::" + info + ": ::\u001B[0m");
    }

    public static <T> void printClass(T object) {
        System.out.println("\u001B[38;5;208m::::::::::::::::::::::::::::" + object.getClass().getSimpleName() + "::::::::::::::::::::::::::::\u001B[0m");
        for (Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                System.out.println("\u001B[38;5;214m::" + field.getName() + "---------------\u001B[0m" + field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    public static <T> void printClassList(List<T> objectList) {
        System.out.println("------------------------------------------------------------------------------------------");
        printInfoMessage("Printing list of " + objectList.get(0).getClass().getSimpleName() + "s");
        objectList.forEach(PrintHelper::printClass);
        printInfoMessage("End of list");
        System.out.println("------------------------------------------------------------------------------------------");


    }


}
