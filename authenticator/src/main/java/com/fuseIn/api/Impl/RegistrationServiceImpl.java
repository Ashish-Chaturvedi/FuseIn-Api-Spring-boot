package com.fuseIn.api.Impl;

import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fuseIn.api.Interface.IRegister;
import com.fuseIn.api.Interface.IRegisterDao;
import com.fuseIn.api.bo.RegisterBO;
import com.fuseIn.api.dao.RegisterDAO;
import com.fuseIn.api.utils.EncodeCredentials;
import com.fuseIn.api.utils.TokenGenerator;
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
		
		TokenGenerator generateIdForUser = new TokenGenerator();
		
		RegisterDAO userDao = new RegisterDAO();
		
		userDao.setFirstName(userBo.getFirstName());
		userDao.setLastName(userBo.getLastName());
		userDao.setAddress(userBo.getAddress());
		userDao.setAge(userBo.getAge());
		userDao.setEmail(userBo.getEmail());
		userDao.setContact(userBo.getContact());
		userDao.setGender(userBo.getGender());
		userDao.setInterest(userBo.getInterest());
		
		try{
			encryptedPass = new EncodeCredentials().encodePassword(userBo.getPassword());
		}catch(NoSuchAlgorithmException exception) {
			logger.error(exception.getMessage());
		}
		String generatedUserIdentity  = generateIdForUser.tokenGenerator();
		
		return this.registerUserDao.create(userDao, encryptedPass);
	}
}
