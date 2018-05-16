package com.fuseIn.api.Impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fuseIn.api.Interface.IRegister;
import com.fuseIn.api.Interface.IRegisterDao;
import com.fuseIn.api.bo.RegisterBO;
import com.fuseIn.api.dao.RegisterDAO;
import com.fuseIn.api.entity.User;
import com.fuseIn.api.utils.EmailNotifierService;
import com.fuseIn.api.utils.Encoder;
/*
 * 
 * @author AshishChaturvedi
 * 
 */

@Service
public class RegistrationServiceImpl implements IRegister {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	public JavaMailSender sendEmail;
	
	@Autowired
	private IRegisterDao registerUserDao;

	public RegistrationServiceImpl(IRegisterDao registerUserDao) {
		this.registerUserDao = registerUserDao;
	}

	public String createUser(RegisterBO userBo) {
		
		Encoder encoder = new Encoder();
		RegisterDAO userDao = new RegisterDAO();

		User isUserExist = findUserInRepository(userBo.getEmail());

		if (isUserExist.getEmail() != null) {
			logger.info("User already exists with username " + isUserExist.getFirstName() + " "
					+ isUserExist.getLastName());
			return "User already Exists with email: +" + isUserExist.getEmail();
		} else {
			userDao.setFirstName(userBo.getFirstName());
			userDao.setLastName(userBo.getLastName());
			userDao.setAddress(userBo.getAddress());
			userDao.setAge(userBo.getAge());
			userDao.setEmail(userBo.getEmail());
			userDao.setContact(userBo.getContact());
			userDao.setGender(userBo.getGender());
			userDao.setInterest(userBo.getInterest());
			userDao.setPassword(userBo.getPassword());
			userDao.setId(encoder.generateJWTTokenforUserVerification(userBo.getEmail()));
			userDao.isEnabled(false);

		}
		return new EmailNotifierService().sendTokenVerificationToUser(this.registerUserDao.createUser(userDao), sendEmail);
	}

	@Override
	public User findUserInRepository(String verificationCheck) {
		return this.registerUserDao.findUserInRepository(verificationCheck);
	}

	@Override
	public String verifyUserForActivation(String token) {
		Encoder encode = new Encoder();
		String tokenStatus = encode.verifyJwtTokenForUser(token);
		if(tokenStatus == "") {
			tokenStatus = "Oppssss!!!! your token has expired....";
			logger.info("token expired.... need to generate a new token for user");
		}
		
		return tokenStatus;
	}
}
