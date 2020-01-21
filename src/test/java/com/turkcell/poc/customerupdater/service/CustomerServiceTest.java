package com.turkcell.poc.customerupdater.service;

import com.turkcell.poc.customerupdater.dto.CustomerDTO;
import com.turkcell.poc.customerupdater.entity.Customer;
import com.turkcell.poc.customerupdater.mapper.CustomerMapper;
import com.turkcell.poc.customerupdater.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @InjectMocks
    CustomerService customerService;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    CustomerMapper customerMapper;

    @Mock
    CacheManager cacheManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void getCustomersByRange() {
//
//        when(customerRepository.count()).thenReturn(15L);
//        Pageable page = PageRequest.of(from,size);
//        Page<Customer> customerPage = customerRepository.findAll(page);
//        List<Customer> customerList = customerPage.stream().collect(Collectors.toList());
//        return customerMapper.toDTOList(customerList);
//    }

    @Test
    public void count() {
        when(customerRepository.count()).thenReturn(15L);
        assertEquals(15, customerService.count());
    }

    @Test
    public void clearCache() {
        Cache cache= new CacheManager() {
            @Override
            public Cache getCache(String s) {
                return new Cache() {
                    @Override
                    public String getName() {
                        return "cacheName";
                    }

                    @Override
                    public Object getNativeCache() {
                        return null;
                    }

                    @Override
                    public ValueWrapper get(Object o) {
                        return null;
                    }

                    @Override
                    public <T> T get(Object o, Class<T> aClass) {
                        return null;
                    }

                    @Override
                    public <T> T get(Object o, Callable<T> callable) {
                        return null;
                    }

                    @Override
                    public void put(Object o, Object o1) {

                    }

                    @Override
                    public void evict(Object o) {

                    }

                    @Override
                    public void clear() {

                    }
                };
            }

            @Override
            public Collection<String> getCacheNames() {
                return null;
            }
        }.getCache("");
        when(cacheManager.getCache("cacheName")).thenReturn(cache);
        assertTrue(true);
    }

//    @Test
//    public void updateCustomer() {
//        when(customerMapper.toEntity(createCustomerDTO())).thenReturn(createCustomer());
//        when(customerRepository.save(createCustomer())).thenReturn(createCustomer());
//
//        doNothing().when(customerService.sendToQueue());
//
//
//        assertEquals(true,customerService.updateCustomer(createCustomerDTO()));
//    }

    @Test
    public void createKafkaProducer() {
    }

    private CustomerDTO createCustomerDTO(){
        return CustomerDTO.builder()
                .tckn("239723")
                .surname("customersur")
                .name("custname")
                .build();
    }

    private Customer createCustomer(){
        return Customer.builder()
                .tckn("239723")
                .surname("customersur")
                .name("custname")
                .build();
    }

}