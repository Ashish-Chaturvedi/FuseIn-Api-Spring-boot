package com.fuseIn.api.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fuseIn.api.Interface.IRegister;
import com.fuseIn.api.bo.RegisterBO;
import com.fuseIn.api.dto.RegisterDTO;
import com.fuseIn.api.utils.Constants;
import com.fuseIn.api.utils.Encoder;

@RestController
@RequestMapping(Constants.REGISTER_API_MAPPING)
public class Registration {

	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private IRegister registerUserBo;

	@PostMapping(path = Constants.REGISTER_USER, consumes = "application/json", produces = "text/plain")
	public ResponseEntity<String> getRegistrationDetails(@RequestBody RegisterDTO userRegistration) {
		
		Map<String,String> encryptedPass = null;
		Encoder encoder = new Encoder();
		
		try {
			encryptedPass = encoder.encodePassword(userRegistration.getPassword());
		} catch (NoSuchAlgorithmException exception) {
			logger.error(exception.getMessage());
		}
		RegisterBO userBo = new RegisterBO();

		userBo.setFirstName(userRegistration.getFirstName());
		userBo.setLastName(userRegistration.getLastName());
		userBo.setGender(userRegistration.getGender());
		userBo.setAddress(userRegistration.getAddress());
		userBo.setAge(userRegistration.getAge());
		userBo.setContact(userRegistration.getContact());
		userBo.setEmail(userRegistration.getEmail());
		userBo.setInterest(userRegistration.getInterest());
		userBo.setPassword(encryptedPass);
		userBo.isEnabled(false);

		return new ResponseEntity<String>(registerUserBo.createUser(userBo), HttpStatus.ACCEPTED);
	}
	
	@GetMapping(path = "/acc_verification", produces = "text/plain")
	public ResponseEntity<String> userAccountVerification(@RequestParam ("token") String token) {
		return new ResponseEntity<String>(registerUserBo.verifyUserForActivation(token), HttpStatus.ACCEPTED);
	}
}
