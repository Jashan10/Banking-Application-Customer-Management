package com.coding.exercise.bankapp;

import com.coding.exercise.bankapp.controller.CustomerController;
import com.coding.exercise.bankapp.domain.AddressDetails;
import com.coding.exercise.bankapp.domain.ContactDetails;
import com.coding.exercise.bankapp.domain.CustomerDetails;
import com.coding.exercise.bankapp.service.BankingServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class)
@WithMockUser
public class CustomControllerTest {

    String exampleCustomerJson = "{\"contactDetails\":{\"emailId\":\"roger@test.com\",\"mobilePhone\":\"123456789\",\"homePhone\":\"8470000000\",\"workPhone\":\"8471112222\"},\"customerAddress\":{\"address1\":\"123 McKee Ave\",\"address2\":\"UNIT 4\",\"city\":\"Chicago\",\"country\":\"USA\",\"state\":\"IL\",\"zip\":\"60076\"},\"customerNumber\":1001,\"firstName\":\"Roger\",\"lastName\":\"Federer\",\"middleName\":\"D\",\"dateOfBirth\":\"2011-10-10\",\"status\":\"Active\"}\n";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BankingServiceImpl bankingService;

    @Test
    public void findByCustomerNumberTest() throws Exception {

        ContactDetails contactDetails = ContactDetails.builder()
                .emailId("roger@test.com")
                .homePhone("8470000000")
                .workPhone("8471112222")
                .mobilePhone("123456789")
                .build();


        AddressDetails addressDetails = AddressDetails.builder().address1("123 McKee Ave")
                .address2("UNIT 4")
                .city("Chicago")
                .zip("60076")
                .state("IL")
                .country("USA")
                .build();


        CustomerDetails customerDetails = CustomerDetails.builder()
                .firstName("Roger")
                .middleName("D")
                .lastName("Federer")
                .dateOfBirth(LocalDate.of(2011, 10, 10))
                .customerNumber(new Long(1001))
                .status("Active")
                .contactDetails(contactDetails)
                .customerAddress(addressDetails)
                .build();

        Mockito.when(
                bankingService.findByCustomerNumber(Mockito.anyLong())).thenReturn(new ResponseEntity(customerDetails, HttpStatus.OK));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/customers/1001").accept(
                MediaType.APPLICATION_JSON).characterEncoding("utf-8");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        System.out.println("result" + result.getResponse()
                .getContentAsString());
        String expected = "{\"contactDetails\":{\"emailId\":\"roger@test.com\",\"mobilePhone\":\"123456789\",\"homePhone\":\"8470000000\",\"workPhone\":\"8471112222\"},\"customerAddress\":{\"address1\":\"123 McKee Ave\",\"address2\":\"UNIT 4\",\"city\":\"Chicago\",\"country\":\"USA\",\"state\":\"IL\",\"zip\":\"60076\"},\"customerNumber\":1001,\"firstName\":\"Roger\",\"lastName\":\"Federer\",\"middleName\":\"D\",\"dateOfBirth\":\"2011-10-10\",\"status\":\"Active\"}\n";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }


    @Test
    public void getAllCustomersLessThanAgeTest() throws Exception {

        ContactDetails contactDetails = ContactDetails.builder()
                .emailId("roger@test.com")
                .homePhone("8470000000")
                .workPhone("8471112222")
                .mobilePhone("123456789")
                .build();


        AddressDetails addressDetails = AddressDetails.builder().address1("123 McKee Ave")
                .address2("UNIT 4")
                .city("Chicago")
                .zip("60076")
                .state("IL")
                .country("USA")
                .build();


        CustomerDetails customerDetails = CustomerDetails.builder()
                .firstName("Roger")
                .middleName("D")
                .lastName("Federer")
                .dateOfBirth(LocalDate.of(2011, 10, 10))
                .customerNumber(new Long(1001))
                .status("Active")
                .contactDetails(contactDetails)
                .customerAddress(addressDetails)
                .build();

        Mockito.when(
                bankingService.getAllCustomersLessThanAge(Mockito.anyInt())).thenReturn(new ResponseEntity(customerDetails, HttpStatus.OK));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/customers/age").accept(
                MediaType.APPLICATION_JSON).param("lt", "18");

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expected = "{\"contactDetails\":{\"emailId\":\"roger@test.com\",\"mobilePhone\":\"123456789\",\"homePhone\":\"8470000000\",\"workPhone\":\"8471112222\"},\"customerAddress\":{\"address1\":\"123 McKee Ave\",\"address2\":\"UNIT 4\",\"city\":\"Chicago\",\"country\":\"USA\",\"state\":\"IL\",\"zip\":\"60076\"},\"customerNumber\":1001,\"firstName\":\"Roger\",\"lastName\":\"Federer\",\"middleName\":\"D\",\"dateOfBirth\":\"2011-10-10\",\"status\":\"Active\"}\n";
        JSONAssert.assertEquals(expected, result.getResponse()
                .getContentAsString(), false);
    }

    @Test
    public void addCustomerTest() throws Exception {

        ContactDetails contactDetails = ContactDetails.builder()
                .emailId("roger@test.com")
                .homePhone("8470000000")
                .workPhone("8471112222")
                .mobilePhone("123456789")
                .build();


        AddressDetails addressDetails = AddressDetails.builder().address1("123 McKee Ave")
                .address2("UNIT 4")
                .city("Chicago")
                .zip("60076")
                .state("IL")
                .country("USA")
                .build();


        CustomerDetails customerDetails = CustomerDetails.builder()
                .firstName("Roger")
                .middleName("D")
                .lastName("Federer")
                .dateOfBirth(LocalDate.of(2011, 10, 10))
                .customerNumber(new Long(1001))
                .status("Active")
                .contactDetails(contactDetails)
                .customerAddress(addressDetails)
                .build();

        Mockito.when(
                bankingService.addCustomer(
                        Mockito.any(CustomerDetails.class))).thenReturn(new ResponseEntity(customerDetails, HttpStatus.CREATED));

        // Send course as body to /students/Student1/courses
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/customers")
                .accept(MediaType.APPLICATION_JSON).content(exampleCustomerJson)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());


    }


}
