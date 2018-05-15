package com.fuseIn.api.daoImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
//import com.datastax.driver.core.Session;
import com.fasterxml.jackson.core.JsonParser;
import com.fuseIn.api.Interface.IRegisterDao;
import com.fuseIn.api.dao.RegisterDAO;
import com.fuseIn.api.entity.User;
import com.fuseIn.api.utils.Constants;
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
	
	@Autowired
	private User user;
	
	private String check = "";
	
	
	@Override
	public String create(RegisterDAO userDao, JSONObject encryptedPass) {
		boolean status = false;
		ResultSet resSet = null;
		
		PropertyUtil proUtil = new PropertyUtil();
		Session session = null;
		String query = "insert into fusein.user(email, age, firstname, id, interest, lastname, password_hash, salt) values ("+"'" + userDao.getEmail() + "',"+"'" + userDao.getAge() + "',"+"'" + userDao.getFirstName()+ "',"+"'"+userDao.getId()+"',"+"'"+ userDao.getInterest()+ "',"+"'" + userDao.getLastName()+ "',"+"'"+encryptedPass.get("encryptedPass")+"',"+"'"+encryptedPass.get("saltValue")+"'"+");";
		try {
				cassObj.connectDb(proUtil.getComponentDetails("node"));
				session = cassObj.getSession();
				resSet = session.execute(query);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		session.close();
		status = resSet.wasApplied();
			
		if (status = true){
			logger.info("User registered Successfully");
		}return userDao.getId();
}

	@Override
	public User findUserInRepository(String verificationCheckwithEmail) {
		
		PropertyUtil proUtil = new PropertyUtil();
		Session session = null;
		try {
		cassObj.connectDb(proUtil.getComponentDetails("node"));
		session = cassObj.getSession();
		ResultSet getUserSet = session.execute("select * from fuseIn.user where email="+"'"+verificationCheckwithEmail+"';");
		
		mapUserToGetResponse(getUserSet);

		}
		catch(Exception e) { logger.error(e.getMessage());}
		return user;
	}

	private User mapUserToGetResponse(ResultSet getUserSet) {
		for(Row row : getUserSet) {
			user.setFirstName(row.getString("firstName"));
			user.setLastName(row.getString("lastName"));
			user.setEmail(row.getString("email"));
		//	user.setContact(row.getString("contact"));
		}
		return user;
	}
}
