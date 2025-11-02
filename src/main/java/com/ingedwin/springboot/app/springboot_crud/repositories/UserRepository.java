package com.ingedwin.springboot.app.springboot_crud.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ingedwin.springboot.app.springboot_crud.entities.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{

}
