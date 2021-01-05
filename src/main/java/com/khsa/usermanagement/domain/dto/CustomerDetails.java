package com.khsa.usermanagement.domain.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class CustomerDetails {

    private String tier;

    private List<String> benefits;

    private Boolean active;

    @Field("id")
    private String id;

}
