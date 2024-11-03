package com.appsdeveloperblog.estore.data;

import com.appsdeveloperblog.estore.model.User;

import java.util.HashMap;
import java.util.Map;

public class UsersRepositoryImpl implements UsersRepository {

    Map<String, User> users = new HashMap<>();

    @Override
    public boolean save(User user) {

        if (!this.users.containsKey(user.getId())) {
            this.users.put(user.getId(), user);
            return true;
        }

        return false;
    }
}
