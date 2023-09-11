package org.example.app.service.impl;

import org.example.app.model.User;
import org.example.app.repository.UserRepository;
import org.example.app.service.CrudService;

import java.util.List;

public class UserService implements CrudService<User> {


    private  static  final  User user = new User();
    private static  final UserRepository userRepository = new UserRepository();
    @Override
    public User save(User user) {
        return user.mapData(userRepository.save(user));
    }

    @Override
    public User select(int id) {
        return user.mapData(userRepository.find(id));
    }

    @Override
    public List<User> selectAll() {
        return user.mapDataList(userRepository.findAll());
    }


    @Override
    public User update(User user, int id) {
        return user.mapData(userRepository.update(user,id));
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }
}
