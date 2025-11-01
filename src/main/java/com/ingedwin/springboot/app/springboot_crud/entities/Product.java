package com.ingedwin.springboot.app.springboot_crud.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String name;

    /*
     * Esta es una manera de personalizar mensajes desde un archivo
     * properties y es recomendado usar @NotBlank cuando sean Strings
     */

    @Min(1)
    @NotNull(message = "{NotNull.Product.price}")
    private Integer price;

    @NotBlank(message = "{NotBlank.Product.description}")
    private String description;

}
