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
	public Map<String, String> create(RegisterDAO text, JSONObject encryptedPass);
	public User findUserInRepository(String verificationCheck);
}
