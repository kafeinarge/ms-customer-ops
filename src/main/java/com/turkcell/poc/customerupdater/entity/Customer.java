package com.turkcell.poc.customerupdater.entity;

import com.turkcell.poc.customerupdater.entity.base.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "tckn")
@Data
@Document(collection = "customers")
public class Customer extends BaseEntity {

    private String tckn;

    private String name;

    private String surname;


}
