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
		PropertyUtil proUtil = new PropertyUtil();
		
		try {
				cassObj.connectDb(proUtil.getComponentDetails("node"));
				Session session = cassObj.getSession();
			/*	resSet = session.execute("insert into fusein.user(email,age,firstname,id,interest,lastname,password_hash,salt) values("+userDao.getEmail(),userDao.getAge()+",'Ashish','007','dance','chaturvedi','asdfv23r','123rfqsd') IF NOT EXISTS;");*/
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		status = resSet.wasApplied();
		//	JSONObject jsonGenerated = new JSONObject(check);
			
		if (status = true)
			check = "User registered Sucessfully";
		return check;
}
}
