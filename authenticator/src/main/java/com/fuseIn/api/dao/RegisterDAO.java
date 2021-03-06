package com.fuseIn.api.dao;

import java.util.Map;

import com.fuseIn.api.entity.Address;
/*
 * 
 * @author AshishChaturvedi
 * 
 */
public class RegisterDAO {
	
	private String id;
	private String firstName;
	private String lastName;
	private String contact;
	private Address address;
	private String age;
	private String gender;
	private String interest;
	private Map<String, String> password;
	private String email;
	private boolean isEnabled;
	
	
	public Map<String, String> getPassword() {
		return password;
	}
	public void setPassword(Map<String, String> password) {
		this.password = password;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getInterest() {
		return interest;
	}
	public void setInterest(String interest) {
		this.interest = interest;
	}
	public boolean isEnabled(boolean b) {
		return isEnabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
}
