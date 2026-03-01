package com.ingedwin.springboot.app.springboot_crud.interfaces;

import java.util.List;

import com.ingedwin.springboot.app.springboot_crud.entities.User;

public interface UserService {

    List<User> findAll();

    User save(User user);

}
