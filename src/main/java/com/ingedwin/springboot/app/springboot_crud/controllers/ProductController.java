package com.ingedwin.springboot.app.springboot_crud.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import com.ingedwin.springboot.app.springboot_crud.entities.Product;
import com.ingedwin.springboot.app.springboot_crud.interfaces.ProductService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping
    public List<Product> list(){
        return productService.findAll();
    }

    /*
     * El ResponseEntity<?> nos va a ayudar a cuando nosotros tengamos que devolver un entity
     * que probablemente no exista en la DB, nos ayuda a manejar los errores en un entorno HTTP mas
     * manejable, profesional y limpio
     * 
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id){
        /*
        return productService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        
        Esto es lo mismo que el return de abajo
        */
        return productService.findById(id).isPresent() ? ResponseEntity.ok(productService.findById(id)) : ResponseEntity.notFound().build() ;
    }

    /*
     * La notacion @Valid es utilizada solamente en entidades (Entities) y son validadores que nos ayudan a
     * tener una mejor restriccion de que tipos de datos o campos vacios evitar en nuestras entradas de
     * datos
     * 
     * El BindingResult nos ayudara a validar errores y poder retornarlos como argumento tipo JSON
     * el cual siempre debe de ir a la derecha de nuestro ENTITY SIEMPRE, no puede ir en otro lugar
     * como parametro
     */

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Product product, BindingResult result) {

        if(result.hasFieldErrors()){
            return validation(result);
        }

        /*
         * Aqui se recomienda utilizar el codigo 200 que esta dentro del metodo OK  del ResponseEntity
         * para confirmar dentro del estandar REST que la acutalización se hizo correctamente, o sea, OK
         * 
         */
        return productService.update(id, product)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {

        if(result.hasFieldErrors()){
            return validation(result);
        }

        /*
         * Se recomienda que para un post se utilice este metodo ya que devuelve un HTTP Code 201
         * el cual dentro del estandar REST es correcto para decir que algo se ha creado
         */
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        // return productService.findById(id).isPresent() ? 
        // ResponseEntity.ok(productService.delete(id)) : 
        // ResponseEntity.notFound().build();

        return productService.delete(id)
                .map(p -> ResponseEntity.noContent().build())
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> error = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            error.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(error);
    }
}
