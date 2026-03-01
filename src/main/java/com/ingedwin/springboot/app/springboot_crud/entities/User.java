package com.ingedwin.springboot.app.springboot_crud.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(unique = true)     // <----- Se utiliza para decir que no puede haber valores repetidos
    private String username;

    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // <---- De esta manera excluimos los atributos del JSON
    private String password;                               //       Para que no se muestren, solo se escriban          

    private boolean enabled;

    @Transient     // <-------- Indica al modelo que esta variable no pertenece a la persistencia de la tabla, es solo de la clase
    private boolean admin;

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"),
        uniqueConstraints = { @UniqueConstraint(columnNames = {"user_id","role_id"})}
    )
    private List<Role> roles;

    public boolean isAdmin(){
        return admin;
    }

    public boolean isEnabled(){
        return enabled;
    }
}
