package org.example.framwork.dao.Helper;

import org.example.framwork.dao.Type.Paragraph;
import org.example.framwork.dao.Type.Text;
import org.example.framwork.dao.utils.config.DatabaseConfig;
import org.example.framwork.dao.database.sql.MysqlQueries;
import org.example.framwork.dao.database.sql.PostgresqlQueries;
import org.example.framwork.dao.database.sql.SqlQueries;

public class Factory {
    private static Text tempText = new Text();
    private static Paragraph tempParagraph = new Paragraph();

    private static SqlQueries sqlQueries;

    public static Text createNewText() {
        tempText = null;
        tempText = new Text();
        return tempText;
    }

    public static Paragraph createNewParagraph() {
        tempParagraph = null;
        tempParagraph = new Paragraph();
        return tempParagraph;
    }

    public static Object createSpecialFieldValue(Class<?> fieldType, String value) {
        if (fieldType.equals(Text.class)) return createNewText().set(value);
        else if (fieldType.equals(Paragraph.class)) return createNewParagraph().set(value);
        else throw new IllegalArgumentException("Invalid special field type");
    }


    public static SqlQueries getSqlQueries() {

        if(sqlQueries != null) return sqlQueries; // use the old object if the program is already run

        if(DatabaseConfig.DATABASE_TYPE.equals("postgresql")) {
            sqlQueries = new PostgresqlQueries();
        } else if(DatabaseConfig.DATABASE_TYPE.equals("mysql")) {
            sqlQueries = new MysqlQueries();
        } else {
            throw new IllegalArgumentException("Unsupported database type: " + DatabaseConfig.DATABASE_TYPE);
        }
        return sqlQueries;
    }

}
