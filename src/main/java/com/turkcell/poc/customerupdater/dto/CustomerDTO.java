package com.turkcell.poc.customerupdater.dto;

import com.turkcell.poc.customerupdater.dto.base.BaseEntityDTO;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(of = "tckn")
@Data
public class CustomerDTO extends BaseEntityDTO {

    private String tckn;

    private String name;

    private String surname;


}
