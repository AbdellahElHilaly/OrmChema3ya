package org.example.app.model;

import org.example.dao.ORM.ModelMapper;

public class User extends ModelMapper<User> {
    private int id;
    private String name;

    public User() {
    }

    public User(String abdellah) {
        this.id = 0;
        this.name = abdellah;
    }

    @Override
    public User createInstance() {
        return new User();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
