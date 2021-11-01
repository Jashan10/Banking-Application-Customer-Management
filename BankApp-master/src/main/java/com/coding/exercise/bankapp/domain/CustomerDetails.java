package com.coding.exercise.bankapp.domain;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CustomerDetails {

    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate dateOfBirth;
    private Long customerNumber;
    private String status;
    private AddressDetails customerAddress;
    private ContactDetails contactDetails;

}
