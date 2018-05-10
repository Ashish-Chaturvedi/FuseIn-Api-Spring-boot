package com.fuseIn.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fuseIn.api.Interface.IRegister;
import com.fuseIn.api.bo.RegisterBO;
import com.fuseIn.api.dto.RegisterDTO;


@RestController
@RequestMapping("/api/register")
public class Registration {
	
	
	@Autowired
	private IRegister registerUserBo;
	
	@PostMapping(path = "/user", consumes = "application/json", produces = "text/plain")
	public ResponseEntity<String> getRegistrationDetails(@RequestBody RegisterDTO userRegistration){
		
		RegisterBO userBo = new RegisterBO();

		userBo.setFirstName(userRegistration.getFirstName());
		userBo.setLastName(userRegistration.getLastName());
		userBo.setGender(userRegistration.getGender());
		userBo.setAddress(userRegistration.getAddress());
		userBo.setAge(userRegistration.getAge());
		userBo.setContact(userRegistration.getContact());
		userBo.setEmail(userRegistration.getEmail());
		userBo.setInterest(userRegistration.getInterest());
		userBo.setPassword(userRegistration.getPassword());

		String check = registerUserBo.create(userBo);
		
		return new ResponseEntity<String>("Sprint Boot application has booted successfully",HttpStatus.ACCEPTED);
	}
}
