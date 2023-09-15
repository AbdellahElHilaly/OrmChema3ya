package org.example.framwork.console.print;

public class Printer extends Colors {
    protected String message;
    protected final static String space = " ";
    protected final static String line = "----------------------------------------";


    public static void info(String message) {

        tempColor = "blue";
        tempEmoji = "info";
        tempStatus = "info";

        showMassage(message, tempStatus);
    }


    public static void warning(String message) {
        tempColor = "yellow";
        tempEmoji = "warning";
        tempStatus = "warning";

        showMassage(message, tempStatus);

    }

    public static void error(String message) {
        tempColor = "red";
        tempEmoji = "error";
        tempStatus = "error";

        showMassage(message, tempStatus);
    }

    public static void success(String message) {
        tempColor = "green";
        tempEmoji = "success";
        tempStatus = "success";

        showMassage(message, tempStatus);
    }


    public static void showMassage(String message, String status) {
        line();
        print(emoji.get(tempEmoji));
        print(status);
        print(emoji.get(tempEmoji));
        lineEnd();
        print(space + message);
        System.out.println();
    }

    public static void set(String message) {
        tempColor = "reset";
        print(message + ": ");
        endl(1);
    }

    public static <T> void printKeyVal(T key, String val) {
        tempColor = "yellow";
        print(key + " : ");
        tempColor = "cyan";
        print(val);
        endl(1);
    }


    public static void line() {
        System.out.print(colorsCode.get(tempColor) + line + colorsCode.get("reset"));
    }

    public static void lineEnd() {
        System.out.println(colorsCode.get(tempColor) + line + colorsCode.get("reset"));
    }

    public static void print(String message) {
        System.out.print(colorsCode.get(tempColor) + message + colorsCode.get("reset"));
    }


    public static void endl(int i) {
        for (int j = 0; j < i; j++) {
            System.out.println();
        }
    }



    public static void printSpace(int i) {
        for (int j = 0; j < i; j++) {
            print(space);
        }
    }



    public static void debug(String message) {
        tempColor = "purple";
        print(message);
        endl(1);
    }

    public static void url(String filePath) {
        tempColor = "cyan";
        print(space + filePath);
        endl(1);
    }
}

