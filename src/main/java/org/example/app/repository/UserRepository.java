package org.example.app.repository;

import org.example.app.model.User;
import org.example.dao.ORM.BaseRepository;

public class UserRepository  extends BaseRepository<User> {
    public UserRepository() {
        super(User.class);
    }
}
