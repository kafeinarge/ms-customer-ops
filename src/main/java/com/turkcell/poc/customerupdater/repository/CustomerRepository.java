package com.turkcell.poc.customerupdater.repository;;

import com.turkcell.poc.customerupdater.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {

    Page<Customer> findAll(Pageable pageable);

}
