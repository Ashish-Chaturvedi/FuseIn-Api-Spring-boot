package com.fuseIn.api.Interface;

import org.json.JSONObject;
import com.fuseIn.api.dao.RegisterDAO;
/*
 * 
 * @author AshishChaturvedi
 * 
 */
public interface IRegisterDao {
	public String create(RegisterDAO text, JSONObject encryptedPass);
}
