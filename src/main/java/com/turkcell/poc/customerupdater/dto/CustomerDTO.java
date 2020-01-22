package com.turkcell.poc.customerupdater.dto;

import com.turkcell.poc.customerupdater.dto.base.BaseEntityDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@EqualsAndHashCode(of = "tckn")
@Data
public class CustomerDTO extends BaseEntityDTO {

    private String tckn;

    private String name;

    private String surname;


}
