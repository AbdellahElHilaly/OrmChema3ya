package org.example;




import java.sql.SQLException;

public class App {
    public static void main(String[] args) throws SQLException {


        UserService userService = new UserService();


        User userToSave = new User("abdellah");

        userToSave = userService.save(userToSave);

        System.out.println(userToSave.getId());
        System.out.println(userToSave.getName());


    }



}










