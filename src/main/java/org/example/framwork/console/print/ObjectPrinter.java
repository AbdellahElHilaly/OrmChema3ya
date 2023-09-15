package org.example.framwork.console.print;


import java.lang.reflect.Field;
import java.util.List;

public class ObjectPrinter extends Printer {

    private static int[] maxSizes ;

    private static void generateMaxFiledSize(String[] headers, String[] body) {
        maxSizes = new int[headers.length];
        for (int i = 0; i < headers.length; i++) {
            maxSizes[i] = headers[i].length();
        }

        for (int i = 0; i < body.length; i++) {
            if (body[i].length() > maxSizes[i]) {
                maxSizes[i] = body[i].length();
            }
        }

    }


    private static <T> void generateGlobalMaxFiledSize(List<T> models){
        if (models.isEmpty()) {
            return;
        }
        maxSizes = new int[ObjectHelper.getModelHeader(models.get(0)).length];
        String[] headers = ObjectHelper.getModelHeader(models.get(0));
        for (int i = 0; i < headers.length; i++) {
            if (headers[i].length() > maxSizes[i]) {
                maxSizes[i] = headers[i].length();
            }
        }
        int k = 1;
        for (T model : models) {
            String[] body = ObjectHelper.getModelBody(model , k++);
            for (int i = 0; i < body.length; i++) {
                if (body[i].length() > maxSizes[i]) {
                    maxSizes[i] = body[i].length();
                }
            }
        }
    }


    private static void printVCases(String color) {
        tempColor = color;
        print("|");
        printSpace(1);
    }

    private static void printHCase(String[] headers, String[] body, String color , String symbol) {
        tempColor = color;
        int realSpace;
        int add = 3;
        for (int i = 0; i < headers.length; i++) {
            realSpace = maxSizes[i] + add;
            for (int j = 0; j < realSpace; j++) {
                print(symbol);
            }
        }
        print(symbol);
        endl(1);
    }



    public static void printField(String key, String value) {
        System.out.print("  ");
        printKeyVal(key, value);
    }

    public static <T> void printClass(T object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();

        System.out.println(clazz.getSimpleName() + " : {");

        for (Field field : fields) {
            field.setAccessible(true);

            String fieldName = field.getName();
            String fieldValue;

            try {
                Object value = field.get(object);
                fieldValue = value != null ? value.toString() : "null";
            } catch (IllegalAccessException e) {
                fieldValue = "N/A";
            }

            printField(fieldName, fieldValue);
        }

        System.out.println("}");
    }

    public static <T> void printClassList(List<T> objects) {
        System.out.println("-------------------------------start list-------------------------------------");
        for (T object : objects) {
            printClass(object);
        }
        System.out.println("-------------------------------end list---------------------------------------");
    }





    private static void printTableHeader(String[] headers, String[] body) {
        printHCase(headers, body, "yellow","=");
        int realSize;

        for (int i = 0; i < headers.length; i++) {
            if (i >= 1) printSpace(1);
            printVCases("yellow");
            tempColor = "cyan";
            print(headers[i]);
            realSize = maxSizes[i] - headers[i].length();
            if (i >= 1) printSpace(realSize);
            if (i==headers.length - 1) printSpace(1);
        }

        printVCases("yellow");
        endl(1);
        printHCase(headers, body, "yellow","=");


    }


    private static void printTableBody(String[] headers, String[] body) {
        int realSize;

        for (int i = 0; i < headers.length; i++) {

            if (i >= 1) printSpace(1);
            printVCases("green");

            tempColor = "white";
            print(body[i]);
            realSize = maxSizes[i] - body[i].length();
            printSpace(realSize);
            if (i==headers.length - 1) printSpace(1);
        }


        printVCases("green");
        endl(1);
        printHCase(headers, body, "green","-");
    }


    public static <T> void printModelTable(T model) {
        String[] headers = LogicHelper.handelHeaderToPrint(ObjectHelper.getModelHeader(model));
        String[] body = LogicHelper.handelBodyToPrint(ObjectHelper.getModelBody(model , 1) , 1);
        generateMaxFiledSize(headers, body);
        printTableHeader(headers, body);
        printTableBody(headers, body);

    }

    public static <T> void printModelsTable(List<T> models) {
        if (models.isEmpty()) {
            return;
        }
        String[] headers = ObjectHelper.getModelHeader(models.get(0));
        String[][] bodies = ObjectHelper.getModelsBody(models);
        generateGlobalMaxFiledSize(models);
        printTableHeader(headers, ObjectHelper.getModelBody(models.get(0) , 1));
        for (String[] body : bodies) {
            printTableBody(headers, body);
        }

    }


}
