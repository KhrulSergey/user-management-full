package com.khsa.usermanagement.domain.model.analytic;

import com.khsa.usermanagement.domain.dto.CustomerDetails;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@Data
@Document(collection = "customers")
public class Customer implements Serializable {

    private static final long serialVersionUID = -2173323865570254572L;

    @Id
    private String id;

//    @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true)
//    @Field(name = "accounts")
//    private List<Account> accounts;

    @Field(name = "accounts")
    private List<Long> accounts;

    @Field(name = "email")
    private String email;

    @Field(name = "name")
    private String name;

    @Field(name = "tier_and_details")
    private HashMap<String, CustomerDetails> tierAndDetails;

    @Field(name = "username")
    private String username;

    @Field(name = "address")
    private String address;

    @Field(name = "birthdate")
    private LocalDate birthdate;
}
