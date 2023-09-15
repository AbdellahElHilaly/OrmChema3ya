package org.example.app.model;

import kotlin.OverloadResolutionByLambdaReturnType;
import org.example.framwork.dao.ORM.ModelMapper;

public class User extends ModelMapper<User> {
    private int  id;
    private String  name;

    public User(String name) {
        this.name = name;
    }

    public User() {

    }

    @Override
    public User createInstance() {
        return new User();
    }


}
