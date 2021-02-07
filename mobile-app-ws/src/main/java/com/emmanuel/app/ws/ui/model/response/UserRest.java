package com.emmanuel.app.ws.ui.model.response;

import java.util.List;

//This class is for returning a response to the user application as jSon document
public class UserRest {

	// delring field tht will sent back as jason document
	private String userId;
	private String firstName;
	private String lastName;
	private String email;
	//Will declare a list from Address class to can be showed in the response object 
	private List<AddressRest> addresses;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String emai) {
		this.email = emai;
	}

	public List<AddressRest> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressRest> addresses) {
		this.addresses = addresses;
	}

}
