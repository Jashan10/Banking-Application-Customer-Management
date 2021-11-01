package com.coding.exercise.bankapp.controller;

import com.coding.exercise.bankapp.domain.CustomerDetails;
import com.coding.exercise.bankapp.service.BankingServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = {"Customer REST endpoints"})
public class CustomerController {

    Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private BankingServiceImpl bankingService;


    /* This method covers US1 */
    @PostMapping(path = "/customers")
    @ApiOperation(value = "Add a Customer", notes = "Add customer and create an account")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})

    public ResponseEntity<Object> addCustomer(@RequestBody CustomerDetails customer) {

        logger.info("Request to addCustomer {}.", customer);
        return bankingService.addCustomer(customer);
    }

    /* This method covers US2 */
    @GetMapping(path = "/customers/{customerNumber}")
    @ApiOperation(value = "Get customer details", notes = "Get Customer details by customer number.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = CustomerDetails.class, responseContainer = "Object"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})

    public ResponseEntity<Object> getCustomer(@PathVariable Long customerNumber) {

        logger.info("Request to getCustomer with customer number {}.", customerNumber);
        return bankingService.findByCustomerNumber(customerNumber);

    }

    /* This method covers US3 */
    @PutMapping(path = "/customers/{customerNumber}")
    @ApiOperation(value = "Update customer", notes = "Update customer and any other account information associated with him.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = Object.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})

    public ResponseEntity<Object> updateCustomerMobileNumber(@RequestBody CustomerDetails customerDetails,
                                                             @PathVariable Long customerNumber) {

        logger.info("Request to updateCustomerMobileNumber with customer number {}.", customerNumber);
        return bankingService.updateCustomerMobileNumber(customerDetails, customerNumber);
    }


    @GetMapping(path = "/customers/age")
    @ApiOperation(value = "Find all customers", notes = "Gets details of all the customers at the bank.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})

    public ResponseEntity<Object> getAllCustomersLessThanAge(@RequestParam(name = "lt") int ageLimit) {

        logger.info("Request to getAllCustomersLessThanAge age {}.", ageLimit);
        return bankingService.getAllCustomersLessThanAge(ageLimit);
    }

    /* This method covers US5 */
    @GetMapping(path = "/customers/{customerNumber}/address")
    @ApiOperation(value = "Get customer address", notes = "Get address for a particular customer.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = Object.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})

    public ResponseEntity<Object> getCustomerAddress(@PathVariable Long customerNumber) {

        logger.info("Request to getCustomerAddress for customer {}.", customerNumber);
        return bankingService.getCustomerAddress(customerNumber);

    }

    /* This method covers US6 */
    @DeleteMapping(path = "customers/{customerNumber}")
    @ApiOperation(value = "Delete customer and related accounts", notes = "Delete customer and all accounts associated with him.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Success", response = Object.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Server Error")})

    public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerNumber) {

        logger.info("Request to deleteCustomer for customer {}.", customerNumber);
        return bankingService.deleteCustomer(customerNumber);
    }


}
