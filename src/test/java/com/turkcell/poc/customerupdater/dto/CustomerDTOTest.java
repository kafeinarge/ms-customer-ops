package com.turkcell.poc.customerupdater.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDTOTest {

    private CustomerDTO customerDTO;

    @Before
    public void setup() {
        customerDTO = new CustomerDTO();
        customerDTO = createCustomerDTO();
    }

    @Test
    public void hashCodeTest() {
        assertNotNull(customerDTO.hashCode());
    }

    @Test
    public void toStringTest() {
        assertNotNull(customerDTO.toString());
    }

    @Test
    public void equalsTest() {
        CustomerDTO documentToCompare = createCustomerDTO();
        CustomerDTO nullRequest = null;
        Object nullObject = null;
        assertTrue(documentToCompare.equals(customerDTO) && customerDTO.equals(documentToCompare));
        assertFalse(customerDTO.equals(nullRequest));
        assertFalse(customerDTO.equals(nullObject));
    }

    private CustomerDTO createCustomerDTO() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setName("XDHJ7846");
        customerDTO.setSurname("Fiber Internet");
        customerDTO.setTckn("3287123");
        return customerDTO;
    }

}