package org.example.framwork.dao.database.sql;


public class PostgresqlQueries extends SqlQueries{

    @Override
    public  String createEmptyTable(String tableName) {
       return "CREATE TABLE IF NOT EXISTS " + tableName + " (id SERIAL PRIMARY KEY) ";
    }

}
