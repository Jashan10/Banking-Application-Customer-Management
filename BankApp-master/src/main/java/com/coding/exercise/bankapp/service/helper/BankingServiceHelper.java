package com.coding.exercise.bankapp.service.helper;

import com.coding.exercise.bankapp.domain.AddressDetails;
import com.coding.exercise.bankapp.domain.ContactDetails;
import com.coding.exercise.bankapp.domain.CustomerDetails;
import com.coding.exercise.bankapp.model.Address;
import com.coding.exercise.bankapp.model.Contact;
import com.coding.exercise.bankapp.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class BankingServiceHelper {

	public CustomerDetails convertToCustomerDomain(Customer customer) {
		
		return CustomerDetails.builder()
				.firstName(customer.getFirstName())
				.middleName(customer.getMiddleName())
				.lastName(customer.getLastName())
				.dateOfBirth(customer.getDateOfBirth())
				.customerNumber(customer.getCustomerNumber())
				.status(customer.getStatus())
				.contactDetails(convertToContactDomain(customer.getContactDetails()))
				.customerAddress(convertToAddressDomain(customer.getCustomerAddress()))
				.build();
	}

	public AddressDetails convertToCustomerAddressDomain(Customer customer) {

		return AddressDetails.builder().address1(customer.getCustomerAddress().getAddress1())
				.address2(customer.getCustomerAddress().getAddress2())
				.address1(customer.getCustomerAddress().getAddress1())
				.city(customer.getCustomerAddress().getCity())
				.zip(customer.getCustomerAddress().getZip())
				.state(customer.getCustomerAddress().getState())
				.country(customer.getCustomerAddress().getCountry())
				.build();
	}
	
	public Customer convertToCustomerEntity(CustomerDetails customerDetails) {
		
		
		return Customer.builder()
				.firstName(customerDetails.getFirstName())
				.middleName(customerDetails.getMiddleName())
				.lastName(customerDetails.getLastName())
				.dateOfBirth(customerDetails.getDateOfBirth())
				.customerNumber(customerDetails.getCustomerNumber())
				.status(customerDetails.getStatus())
				.contactDetails(convertToContactEntity(customerDetails.getContactDetails()))
				.customerAddress(convertToAddressEntity(customerDetails.getCustomerAddress()))
				.build();
	}

	public AddressDetails convertToAddressDomain(Address address) {
		
		return AddressDetails.builder().address1(address.getAddress1())
				.address2(address.getAddress2())
				.city(address.getCity())
				.state(address.getState())
				.zip(address.getZip())
				.country(address.getCountry())
				.build();
	}

	public Address convertToAddressEntity(AddressDetails addressDetails) {
		
		return Address.builder().address1(addressDetails.getAddress1())
				.address2(addressDetails.getAddress2())
				.city(addressDetails.getCity())
				.state(addressDetails.getState())
				.zip(addressDetails.getZip())
				.country(addressDetails.getCountry())
				.build();
	}
	
	public ContactDetails convertToContactDomain(Contact contact) {
		
		return ContactDetails.builder()
				.emailId(contact.getEmailId())
				.homePhone(contact.getHomePhone())
				.workPhone(contact.getWorkPhone())
				.mobilePhone(contact.getMobilePhone())
				.build();
	}
	
	public Contact convertToContactEntity(ContactDetails contactDetails) {
		
		return Contact.builder()
				.emailId(contactDetails.getEmailId())
				.homePhone(contactDetails.getHomePhone())
				.workPhone(contactDetails.getWorkPhone())
				.mobilePhone(contactDetails.getMobilePhone())
				.build();
	}
}
