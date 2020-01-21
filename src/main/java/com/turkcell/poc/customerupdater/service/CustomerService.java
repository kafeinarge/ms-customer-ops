package com.turkcell.poc.customerupdater.service;

import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.turkcell.poc.customerupdater.dto.CustomerDTO;
import com.turkcell.poc.customerupdater.entity.Customer;
import com.turkcell.poc.customerupdater.exception.EntityNotFoundException;
import com.turkcell.poc.customerupdater.mapper.CustomerMapper;
import com.turkcell.poc.customerupdater.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService {

    @Value("${kafka-client-id}")
    private String kafkaClientId;

    @Value("${kafka-bootstrap-servers}")
    private String kafkaBootstrapServers;

    @Value("${kafka-producer-key-serializer}")
    private String kafkaProducerKeySerializer;

    @Value("${kafka-producer-value-serializer}")
    private String kafkaProducerValueSerializer;

    @Value("${kafka-customer-topic}")
    private String kafkaCustomerTopic;

    final
    CacheManager cacheManager;

    final
    CustomerMapper customerMapper;

    final
    CustomerRepository customerRepository;

    /**
     * all injections are set on constructor
     * @param customerMapper
     * @param customerRepository
     * @param cacheManager
     */
    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository, CacheManager cacheManager) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
        this.cacheManager = cacheManager;
    }

    /**
     * getting customer with from and size dividing data size to size
     * @param from
     * @param size
     * @return
     */
    @Cacheable("customers")
    public List<CustomerDTO> getCustomersByRange(Integer from, Integer size) {
        Pageable page = PageRequest.of(from,size);
        Page<Customer> customerPage = customerRepository.findAll(page);
        List<Customer> customerList = customerPage.stream().collect(Collectors.toList());
        return customerMapper.toDTOList(customerList);
    }

    /**
     * find count of all customer collection
     * @return
     */
    public long count() {
        return customerRepository.count();
    }

    /**
     * clearing cache using param that any cache's name. Uses when any store/updates
     * @param cacheName
     */
    @Async
    void clearCache(String cacheName){
        cacheManager.getCache(cacheName).clear();
    }

    /**
     * update customer with DTO object
     * @param customerDTO
     * @return
     */
    public boolean updateCustomer(CustomerDTO customerDTO) {
        clearCache("customers");
        Customer customer = customerMapper.toEntity(customerDTO);

        Customer newVersionOfCustomer = customerRepository.save(customer);

        sendToQueue(newVersionOfCustomer);

        return true;
    }

    /**
     * send customer with new values to kafka
     *
     * @param newVersionOfCustomer
     */
    private void sendToQueue(Customer newVersionOfCustomer) {
        String topicName = kafkaCustomerTopic;

        Producer producer = createKafkaProducer();

        Gson gson = new Gson();
        String customerJsonToKafka = gson.toJson(newVersionOfCustomer);

        ProducerRecord<String, String> rec = new ProducerRecord<>(
                topicName, customerJsonToKafka);
        try {
            producer.send(rec);
        }
        catch (Exception e) {
            log.error("Customer cannot send data successfully");
            throw new KafkaException();
        }
        producer.close();

        log.info("Customer has sent to Kafka successfully");
    }

    /**
     * returns producer using kafka properties
     *
     * @return
     */
    public Producer createKafkaProducer() {
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaClientId);
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaBootstrapServers);
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                kafkaProducerKeySerializer);
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                kafkaProducerValueSerializer);

        return new KafkaProducer<String, String>(configProperties);
    }

}
