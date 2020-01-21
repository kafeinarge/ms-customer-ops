package com.turkcell.poc.customerupdater.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Data
@SuperBuilder
public abstract class BaseEntity implements Serializable {

    @Id
    private String id;

    @CreatedDate
    @Field(value = "created_at")
    private Date createdAt;

    @LastModifiedDate
    @Field(value = "updated_at")
    private Date updatedAt;

}
