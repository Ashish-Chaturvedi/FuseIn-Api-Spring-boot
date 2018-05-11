package com.fuseIn.api.daoImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
//import com.datastax.driver.core.Session;
import com.fasterxml.jackson.core.JsonParser;
import com.fuseIn.api.Interface.IRegisterDao;
import com.fuseIn.api.dao.RegisterDAO;
import com.fuseIn.api.utils.PropertyUtil;
import com.fuseIn.connector.Cassandra;

/*
 * 
 * @author AshishChaturvedi
 * 
 */

@Repository
public class RegistrationDaoImpl implements IRegisterDao {
	
	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	private Cassandra cassObj;
	
	private String check = "";
	ResultSet resSet = null;

	public String create(RegisterDAO userDao, JSONObject encryptedPass) {
		boolean status = false;
		String id = "007";
		PropertyUtil proUtil = new PropertyUtil();
		Session session = null;
		try {
				cassObj.connectDb(proUtil.getComponentDetails("node"));
				session = cassObj.getSession();
			resSet = session.execute("insert into fusein.user(email, age, firstname, id, interest, lastname, password_hash, salt) values ("+"'" + userDao.getEmail() + "',"+"'" + userDao.getAge() + "',"+"'" + userDao.getFirstName()+ "',"+"'"+id+"',"+"'"+ userDao.getInterest()+ "',"+"'" + userDao.getLastName()+ "',"+"'"+encryptedPass.get("encryptedPass")+"',"+"'"+encryptedPass.get("saltValue")+"'"+");");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		session.close();
		status = resSet.wasApplied();
			
		if (status = true)
			check = "User registered Sucessfully";
		return check;
}
}
