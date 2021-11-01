package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.domain.CustomerDetails;
import org.springframework.http.ResponseEntity;

public interface BankingService {


    public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails);
    
    public ResponseEntity<Object> findByCustomerNumber(Long customerNumber);
    
    public ResponseEntity<Object> updateCustomerMobileNumber(CustomerDetails customerDetails, Long customerNumber);
    
    public ResponseEntity<Object> deleteCustomer(Long customerNumber) ;

    public ResponseEntity<Object> getCustomerAddress(Long customerNumber);

    public ResponseEntity<Object> getAllCustomersLessThanAge(int age);
}
