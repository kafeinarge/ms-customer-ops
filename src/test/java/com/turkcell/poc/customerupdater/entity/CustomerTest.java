package com.turkcell.poc.customerupdater.entity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CustomerTest {

    private Customer customer;

    @Before
    public void setup() {
        customer = new Customer();
        customer = createCustomer();
    }

    @Test
    public void hashCodeTest() {
        assertNotNull(customer.hashCode());
    }

    @Test
    public void toStringTest() {
        assertNotNull(customer.toString());
    }

    @Test
    public void equalsTest() {
        Customer documentToCompare = createCustomer();
        Customer nullRequest = null;
        Object nullObject = null;
        assertTrue(documentToCompare.equals(customer) && customer.equals(documentToCompare));
        assertFalse(customer.equals(nullRequest));
        assertFalse(customer.equals(nullObject));
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setName("XDHJ7846");
        customer.setSurname("Fiber Internet");
        customer.setTckn("3287123");
        return customer;
    }

}