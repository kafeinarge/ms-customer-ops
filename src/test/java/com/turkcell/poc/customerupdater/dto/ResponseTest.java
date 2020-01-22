package com.turkcell.poc.customerupdater.dto;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ResponseTest {

    private Response response;

    @Before
    public void setup() {
        response = createResponse();
    }

    @Test
    public void hashCodeTest() {
        assertNotNull(response.hashCode());
    }

    @Test
    public void toStringTest() {
        assertNotNull(response.toString());
    }

    @Test
    public void equalsTest() {
        Response documentToCompare = createResponse();
        Response nullRequest = null;
        Object nullObject = null;
        assertTrue(documentToCompare.equals(response) && response.equals(documentToCompare));
        assertFalse(response.equals(nullRequest));
        assertFalse(response.equals(nullObject));
    }

    private Response createResponse() {
       return Response.builder()
                .errorDescription("error")
                .data("data")
                .httpStatus(200)
                .build();
    }

}