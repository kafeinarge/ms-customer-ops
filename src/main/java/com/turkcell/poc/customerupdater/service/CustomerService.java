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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Properties;

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
    CustomerMapper customerMapper;

    final
    CustomerRepository customerRepository;

    public CustomerService(CustomerMapper customerMapper, CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    public boolean updateCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);

        Customer newVersionOfCustomer = customerRepository.save(customer);

        sendToQueue(newVersionOfCustomer);

        return true;
    }

    /**
     * send customer with new values to kafka
     * @param newVersionOfCustomer
     */
    private void sendToQueue(Customer newVersionOfCustomer) {

        String topicName = kafkaCustomerTopic;

        Properties kafkaProperties = createKafkaProperties();
        Producer producer = new KafkaProducer<String, String>(kafkaProperties);

        Gson gson = new Gson();
        String customerJsonToKafka = gson.toJson(newVersionOfCustomer);

        ProducerRecord<String, String> rec = new ProducerRecord<>(
                topicName, customerJsonToKafka);
        try {
            producer.send(rec);
        } catch (Exception e) {
            log.error("Customer cannot send data successfully");
            throw new KafkaException();
        }
        producer.close();

        log.info("Customer has sent to Kafka successfully");
    }

    /**
     * returns properties of kafka
     *
     * @return
     */
    public Properties createKafkaProperties() {
        Properties configProperties = new Properties();
        configProperties.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaClientId);
        configProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaBootstrapServers);
        configProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                kafkaProducerKeySerializer);
        configProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                kafkaProducerValueSerializer);

        return configProperties;
    }

}
