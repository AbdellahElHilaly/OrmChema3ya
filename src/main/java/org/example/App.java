package org.example;




import org.example.app.model.User;
import org.example.app.service.UserService;
import org.example.framwork.console.print.ObjectPrinter;

import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {




        User userToSave = new User("abdellah");


        ObjectPrinter.printModelTable(userToSave);





    }



}










