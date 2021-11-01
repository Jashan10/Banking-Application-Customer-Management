package com.coding.exercise.bankapp.service;

import com.coding.exercise.bankapp.domain.AddressDetails;
import com.coding.exercise.bankapp.domain.CustomerDetails;
import com.coding.exercise.bankapp.exceptions.ResourceNotFoundException;
import com.coding.exercise.bankapp.model.Contact;
import com.coding.exercise.bankapp.model.Customer;
import com.coding.exercise.bankapp.repository.CustomerRepository;
import com.coding.exercise.bankapp.service.helper.BankingServiceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class BankingServiceImpl implements BankingService {

    Logger logger = LoggerFactory.getLogger(BankingServiceImpl.class);


    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankingServiceHelper bankingServiceHelper;

    public BankingServiceImpl(CustomerRepository repository) {
        this.customerRepository = repository;
    }

    /**
     * CREATE Customer
     *
     * @param customerDetails
     * @return
     */
    public ResponseEntity<Object> addCustomer(CustomerDetails customerDetails) {

        logger.info("Entry addCustomer in BankingServiceImpl with customer details {}.", customerDetails);

        Customer customer = bankingServiceHelper.convertToCustomerEntity(customerDetails);
        customer.setCreateDateTime(new Date());
        customerRepository.save(customer);

        logger.info("Exit addCustomer in BankingServiceImpl.");

        return ResponseEntity.status(HttpStatus.CREATED).body("New Customer created successfully.");
    }

    /**
     * GET Customer
     *
     * @param customerNumber
     * @return
     */

    public ResponseEntity<Object> findByCustomerNumber(Long customerNumber) {

        logger.info("Entry findByCustomerNumber in BankingServiceImpl with customer number {}.", customerNumber);

        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

        if (customerEntityOpt.isPresent()) {
            CustomerDetails customerDetails = bankingServiceHelper.convertToCustomerDomain(customerEntityOpt.get());
            logger.info("Exit findByCustomerNumber in BankingServiceImpl.");
            return new ResponseEntity<Object>(customerDetails, HttpStatus.OK);

        } else
            throw new ResourceNotFoundException("Invalid customer number : " + customerNumber);
    }

    /**
     * UPDATE Customer mobile number
     *
     * @param customerDetails
     * @param customerNumber
     * @return
     */
    public ResponseEntity<Object> updateCustomerMobileNumber(CustomerDetails customerDetails, Long customerNumber) {

        logger.info("Entry updateCustomerMobileNumber in BankingServiceImpl for customer {}.", customerNumber);

        Optional<Customer> managedCustomerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);
        if (managedCustomerEntityOpt.isPresent()) {
            Customer managedCustomerEntity = managedCustomerEntityOpt.get();
            if (Optional.ofNullable(customerDetails.getContactDetails()).isPresent()) {
                Contact managedContact = managedCustomerEntity.getContactDetails();
                if (managedContact != null) {
                    managedContact.setMobilePhone(customerDetails.getContactDetails().getMobilePhone());
                }
            }
            managedCustomerEntity.setUpdateDateTime(new Date());
            customerRepository.save(managedCustomerEntity);
            logger.info("Exit updateCustomerMobileNumber in BankingServiceImpl.");
            return ResponseEntity.status(HttpStatus.OK).body("Success: Customer updated.");
        } else {
            throw new ResourceNotFoundException("Invalid customer number : " + customerNumber);
        }
    }

    /**
     * DELETE Customer
     *
     * @param customerNumber
     * @return
     */
    public ResponseEntity<Object> deleteCustomer(Long customerNumber) {

        logger.info("Entry deleteCustomer in BankingServiceImpl for customer {}.", customerNumber);

        Optional<Customer> managedCustomerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

        if (managedCustomerEntityOpt.isPresent()) {
            Customer managedCustomerEntity = managedCustomerEntityOpt.get();
            customerRepository.delete(managedCustomerEntity);
            logger.info("Exit deleteCustomer in BankingServiceImpl.");

            return ResponseEntity.status(HttpStatus.OK).body("Success: Customer deleted.");
        } else {
            throw new ResourceNotFoundException("Invalid customer number : " + customerNumber);
        }
    }

    /**
     * GET Customer Address
     *
     * @param customerNumber
     * @return
     */
    public ResponseEntity<Object> getCustomerAddress(Long customerNumber) {

        logger.info("Entry getCustomerAddress in BankingServiceImpl for customer {}.", customerNumber);


        Optional<Customer> customerEntityOpt = customerRepository.findByCustomerNumber(customerNumber);

        if (customerEntityOpt.isPresent()) {
            AddressDetails addressDetails = bankingServiceHelper.convertToCustomerAddressDomain(customerEntityOpt.get());

            logger.info("Exit getCustomerAddress in BankingServiceImpl.");

            return new ResponseEntity<Object>(addressDetails, HttpStatus.OK);

        } else
            throw new ResourceNotFoundException("Invalid customer number : " + customerNumber);

    }

    /**
     * List of Customers based on age limit
     *
     * @param ageLimit
     * @return
     */
    @Override
    public ResponseEntity<Object> getAllCustomersLessThanAge(int ageLimit) {

        logger.info("Entry getAllCustomersLessThanAge in BankingServiceImpl .");

        List<CustomerDetails> allCustomerDetails = new ArrayList<>();

        Iterable<Customer> customerList = customerRepository.findAll();

        LocalDate fromBirthDate;
        LocalDate dateToday = LocalDate.now();

        for (Customer customer : customerList) {
            fromBirthDate = customer.getDateOfBirth();
            Period age = Period.between(fromBirthDate, dateToday);
            if (age.getYears() <= 18) {
                allCustomerDetails.add(bankingServiceHelper.convertToCustomerDomain(customer));
            }
        }
        logger.info("Exit getAllCustomersLessThanAge in BankingServiceImpl .");

        return new ResponseEntity<Object>(allCustomerDetails, HttpStatus.OK);
    }
}
