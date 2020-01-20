package com.turkcell.poc.customerupdater.repository;

import com.turkcell.poc.customerupdater.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String > {
}
