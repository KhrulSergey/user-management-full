package com.khsa.usermanagement.domain.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    private static final long serialVersionUID = 1623413704426592666L;

    //Properties
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private long id;


    @Column(name = "name", unique = true)
    private String name;


    @Override
    public String getAuthority() {
        return name;
    }
}
