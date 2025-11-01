package com.ingedwin.springboot.app.springboot_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ingedwin.springboot.app.springboot_crud.entities.Product;
import com.ingedwin.springboot.app.springboot_crud.interfaces.ProductService;
import com.ingedwin.springboot.app.springboot_crud.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Product> findAll() {
        return (List<Product>) productRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Transactional
    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Optional<Product> delete(Long id) {
        
        /*
         * Recomendación general:
         * Si tu método solo elimina y devuelve el producto, usa la versión con map() → más limpia y moderna.
         * Si planeas agregar logs, validaciones o acciones adicionales, conserva la versión con ifPresent().
         * 
         * Optional<Product> productDB = productRepository.findById(id);
         * productDB.ifPresent(p -> {
         *         productRepository.delete(p);
         *         System.out.println("Se ha eliminado el usuario "+ p.getName());
         *      });
         * return productDB;
         * 
         */
        
        return productRepository.findById(id).map(p -> {
            productRepository.delete(p);
            return p;
        });
    }

    @Transactional
    @Override
    public Optional<Product> update(Long id, Product product) {
        // Optional<Product> productDB = productRepository.findById(id);
        // if(productDB.isPresent()){

        //     Product p = productDB.orElseThrow();
        //     p.setName(product.getName());
        //     p.setPrice(product.getPrice());
        //     p.setDescription(product.getDescription());

        //     return Optional.of(p);
        // }
        // return productDB;

        return productRepository.findById(id).map(p ->{
            p.setName(product.getName());
            p.setPrice(product.getPrice());
            p.setDescription(product.getName());
            return productRepository.save(p);
        });
    }
    

}
