package com.ingedwin.springboot.app.springboot_crud.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ingedwin.springboot.app.springboot_crud.entities.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long>{

}
