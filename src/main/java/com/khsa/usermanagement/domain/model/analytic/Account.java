package com.khsa.usermanagement.domain.model.analytic;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.List;

@Data
@Document(collection = "accounts")
public class Account implements Serializable {

    private static final long serialVersionUID = 9164760749215998144L;

    @Id
    private String id;

    @Field(name = "account_id")
    private Long accountId;

    @Field(name = "limit")
    private Long limit;

    @Field(name = "roles")
    private List<String> roles;

}
