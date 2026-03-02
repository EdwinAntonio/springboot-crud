package com.ingedwin.springboot.app.springboot_crud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ingedwin.springboot.app.springboot_crud.entities.User;
import com.ingedwin.springboot.app.springboot_crud.interfaces.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> list(){
        return userService.findAll();
    }

    /*
        Este metodo me ayuda a asegurarme que al momento de crear un usuario, éste no tenga permisos de Administrador, setando el valor
        Admin en FALSE y después ahora si mandamos a llamar el metodo para crear usuarios, de esta manera agregamos otra capa de 
        seguridad a nuestro aplicativo
    */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result){
        user.setAdmin(false);  
        return create(user, result);
    }

    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result){
        
        return  result.hasFieldErrors() ? 
                     validation(result) : 
                     ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }


    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> error = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            error.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(error);
    }
}
