package com.khsa.usermanagement.domain.model.analytic;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Document(collection = "events")
public class Event implements Serializable {

    private static final long serialVersionUID = -4373803876002048283L;

    @Id
    private String id;

    @Field(name = "message")
    private String message;

    @CreationTimestamp
    @Field(name = "created_date")
    private LocalDateTime createdDate;
}

