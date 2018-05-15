package com.fuseIn.api.Impl;

import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.fuseIn.api.Interface.IRegister;
import com.fuseIn.api.Interface.IRegisterDao;
import com.fuseIn.api.bo.RegisterBO;
import com.fuseIn.api.dao.RegisterDAO;
import com.fuseIn.api.entity.User;
import com.fuseIn.api.utils.EmailNotifierService;
import com.fuseIn.api.utils.EncodeCredentials;
/*
 * 
 * @author AshishChaturvedi
 * 
 */

@Service
public class RegistrationServiceImpl implements IRegister {

	private static Logger logger = LogManager.getLogger();

	@Autowired
	private IRegisterDao registerUserDao;

	public RegistrationServiceImpl(IRegisterDao registerUserDao) {
		this.registerUserDao = registerUserDao;
	}

	public String create(RegisterBO userBo) {

		JSONObject encryptedPass = null;
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
			userDao.setId(UUID.randomUUID().toString());
			userDao.isEnabled(false);

			try {
				encryptedPass = new EncodeCredentials().encodePassword(userBo.getPassword());
			} catch (NoSuchAlgorithmException exception) {
				logger.error(exception.getMessage());
			}
		}
		return sendTokenVerificationToUser(this.registerUserDao.create(userDao, encryptedPass));
	}

	private String sendTokenVerificationToUser(Map<String, String> map) {

		SimpleMailMessage mailBuilder = new SimpleMailMessage();
		mailBuilder.setTo(map.get("email"));
		mailBuilder.setSubject("Registration confirmation for FuseIn Account");
		mailBuilder.setText(
				"To activate your account, you need to verify your email address...\n Please click on the link to verify "
						+ map.get("token"));
		mailBuilder.setFrom("NoReply@fusein.com");
		EmailNotifierService emailService = new EmailNotifierService();
		// emailService.SendEmailToUser(mailBuilder);
		return "email sent";
	}

	@Override
	public User findUserInRepository(String verificationCheck) {
		return this.registerUserDao.findUserInRepository(verificationCheck);
	}
}
