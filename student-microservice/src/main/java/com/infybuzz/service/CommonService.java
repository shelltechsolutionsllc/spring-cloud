package com.infybuzz.service;

import com.infybuzz.feignclients.AddressFeignClient;
import com.infybuzz.response.AddressResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonService {

    Logger logger = LoggerFactory.getLogger(CommonService.class);

    int count = 0;

    @Autowired
    AddressFeignClient addressFeignClient;

    @CircuitBreaker(name = "addressService", fallbackMethod = "fallbackGetAddressById")
    public AddressResponse getAddressById(long addressId) {
        logger.info("count = " + count);
        count++;
        return addressFeignClient.getById(addressId);
    }

    public AddressResponse fallbackGetAddressById(long addressId, Throwable th) {
        logger.error("Error = " + th);
        return new AddressResponse();
    }
}
