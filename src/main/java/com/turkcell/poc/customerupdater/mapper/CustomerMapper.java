package com.turkcell.poc.customerupdater.mapper;

import com.turkcell.poc.customerupdater.dto.CustomerDTO;
import com.turkcell.poc.customerupdater.entity.Customer;
import com.turkcell.poc.customerupdater.mapper.base.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class CustomerMapper implements BaseMapper<Customer, CustomerDTO> {

    @Override
    public Customer toEntity(CustomerDTO object) {

        if (object == null)
            return null;

        return Customer
                .builder()
                .name(object.getName())
                .surname(object.getSurname())
                .tckn(object.getTckn())
                .createdAt(new Date())
                .id(object.getId())
                .build();
    }

    @Override
    public CustomerDTO toDTO(Customer object) {

        if (object == null)
            return null;

        return CustomerDTO
                .builder()
                .name(object.getName())
                .surname(object.getSurname())
                .tckn(object.getTckn())
                .createdAt(object.getCreatedAt())
                .updatedAt(object.getUpdatedAt())
                .id(object.getId())
                .build();
    }

}
