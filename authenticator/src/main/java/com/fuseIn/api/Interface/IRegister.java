package com.fuseIn.api.Interface;

import com.fuseIn.api.bo.RegisterBO;
import com.fuseIn.api.entity.User;
/*
 * 
 * @author AshishChaturvedi
 * 
 */
public interface IRegister {
	public String create(RegisterBO text);
	public User findUserInRepository(String verificationCheck);
}
