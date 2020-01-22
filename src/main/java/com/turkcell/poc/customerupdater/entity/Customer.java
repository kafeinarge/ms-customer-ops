package com.turkcell.poc.customerupdater.entity;

import com.turkcell.poc.customerupdater.entity.base.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(of = "tckn")
@Data
@Document(collection = "customers")
public class Customer extends BaseEntity {

    @Field("tckn")
    private String tckn;

    @Field("name")
    private String name;

    @Field("surname")
    private String surname;

    public String getTckn() {
        return tckn;
    }

}
