package com.example.lilactests.model.User;

import com.example.lilactests.model.domain.Note;
import com.example.lilactests.model.domain.User;

import java.util.List;

/**
 * Created by Eventory on 2017/5/19.
 * UserModel Interface
 */
public interface IUserModel {
    boolean addUser(User user);

    boolean updateUser(User user);

    void deleteUser(Long id);

    List<User> selectAll();

    User selectUser(long id);
}
