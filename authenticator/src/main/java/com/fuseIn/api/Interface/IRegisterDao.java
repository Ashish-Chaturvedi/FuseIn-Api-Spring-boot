package com.fuseIn.api.Interface;

import java.util.Map;

import org.json.JSONObject;
import com.fuseIn.api.dao.RegisterDAO;
import com.fuseIn.api.entity.User;
/*
 * 
 * @author AshishChaturvedi
 * 
 */
public interface IRegisterDao {
	public User findUserInRepository(String verificationCheck);
	Map<String, String> createUser(RegisterDAO userDao);
}
