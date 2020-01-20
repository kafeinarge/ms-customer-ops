package com.turkcell.poc.customerupdater.entity.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@EqualsAndHashCode(of = "id")
@Data
public abstract class BaseEntity implements Serializable {

    @Id
    private String id;

    @Field(value = "created_at")
    private Date createdAt;

    @Field(value = "updated_at")
    private Date updatedAt;

}
