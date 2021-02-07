package com.emmanuel.app.ws.ui.model.request;

import java.util.List;

public class UserDetailsRequestModel {

	//Creeating fields for POST method request in this model request class
	//This class will be used to convert the json structure or xml File into Java object
	//when we pass this instnce objecto into data transfer object
	
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	//This list will let ut to sore addresses info 
	private List<AddressRequestModel> addresses;
	
	
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<AddressRequestModel> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<AddressRequestModel> addresses) {
		this.addresses = addresses;
	}
	
	// creating getters and setters 
	
	
}
