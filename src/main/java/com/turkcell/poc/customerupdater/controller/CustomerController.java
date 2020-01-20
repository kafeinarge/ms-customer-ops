package com.turkcell.poc.customerupdater.controller;

import com.turkcell.poc.customerupdater.dto.CustomerDTO;
import com.turkcell.poc.customerupdater.dto.Response;
import com.turkcell.poc.customerupdater.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.turkcell.poc.customerupdater.util.CustomerConstants.CUSTOMER_FAILED;
import static com.turkcell.poc.customerupdater.util.CustomerConstants.CUSTOMER_SUCCESS;

@RestController
@RequestMapping("/customer")
@Slf4j
public class CustomerController {

    final
    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public Response createCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            customerService.updateCustomer(customerDTO);
            return Response.builder()
                    .httpStatus(HttpStatus.OK.value())
                    .data(CUSTOMER_SUCCESS)
                    .build();
        }
        catch (Exception e) {
            log.error("Customer cannot be updated. Id -> " + customerDTO.getId());
            return Response.builder()
                    .httpStatus(HttpStatus.SERVICE_UNAVAILABLE.value())
                    .data(CUSTOMER_FAILED)
                    .build();
        }
    }


}
