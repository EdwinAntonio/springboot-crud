package com.ingedwin.springboot.app.springboot_crud.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingedwin.springboot.app.springboot_crud.entities.Role;
import com.ingedwin.springboot.app.springboot_crud.entities.User;
import com.ingedwin.springboot.app.springboot_crud.interfaces.UserService;
import com.ingedwin.springboot.app.springboot_crud.repositories.RoleRepository;
import com.ingedwin.springboot.app.springboot_crud.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Transactional
    @Override
    public User save(User user) {
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");
        List<Role> roleList = new ArrayList<>();

        optionalRole.ifPresent(roleList::add);
        if (user.isAdmin()){
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roleList::add);
        }
        user.setRoles(roleList);

        /*
         * De esta manera se puede encryptar el password con Spring Security
         */
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

}
