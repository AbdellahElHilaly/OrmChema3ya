package org.example.app.service;

import org.example.app.model.User;
import org.example.framwork.dao.ORM.CrudOperations;
import org.example.framwork.dao.guide.CrudService;

import java.util.List;

public class UserService implements CrudService<User> {
    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User select(int id) {
        return null;
    }

    @Override
    public List<User> selectAll() {
        return null;
    }

    @Override
    public User update(User user, int id) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
