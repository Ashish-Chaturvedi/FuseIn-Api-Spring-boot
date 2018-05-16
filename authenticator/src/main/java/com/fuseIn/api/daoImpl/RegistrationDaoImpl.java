package com.fuseIn.api.daoImpl;

import java.util.HashMap;
import java.util.Map;

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
import com.fuseIn.api.utils.ConnectionsDataSource;
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
	private User user;
	
	private Session session = null;
	
	@Override
	public Map<String, String> createUser(RegisterDAO userDao) {
		
		ResultSet resSet = null;
		
		String query = "insert into fusein.user(email, age, firstname, id, interest, lastname, password_hash, salt, status, address, contact) values ("
				+ "'" + userDao.getEmail() + "'," + "'" + userDao.getAge() + "'," + "'" + userDao.getFirstName() + "',"
				+ "'" + userDao.getId() + "'," + "'" + userDao.getInterest() + "'," + "'" + userDao.getLastName() + "',"
				+ "'" + userDao.getPassword().get("password_hash") + "'," + "'" + userDao.getPassword().get("salt") + "'," + "'"
				+ userDao.isEnabled(false) + "'," + "'" + userDao.getAddress().getCountry() + "'," + "'"
				+ userDao.getContact() + "'" + ");";
		try {
			resSet = this.session.execute(query);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}finally{
			session.close();
		}

		if (resSet.wasApplied()) 
			logger.info("User " + userDao.getFirstName() + " registered Successfully");
		
		
		return prepareDataForVerification(userDao);
	}

	@Override
	public User findUserInRepository(String userEmail) {
		
		ResultSet getUserSet = null;
		ConnectionsDataSource dataSource = new ConnectionsDataSource();
		try {
			session = dataSource.getConnectionFromDatasource();
			getUserSet = session
					.execute("select * from fuseIn.user where email=" + "'" + userEmail + "';");

			if (getUserSet != null)
				user = mapUserObjectWithGetData(getUserSet);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return user;
	}

	private Map<String, String> prepareDataForVerification(RegisterDAO userDao) {
		
		Map<String,String> userMap = new HashMap<String,String>();
		userMap.put("email", userDao.getEmail());
		userMap.put("token", userDao.getId());
		return userMap;
	}

	private User mapUserObjectWithGetData(ResultSet getUserSet) {
		
		User user = new User();
		
		for (Row row : getUserSet) {
			user.setFirstName(row.getString("firstName"));
			user.setLastName(row.getString("lastName"));
			user.setEmail(row.getString("email"));
		}
		return user;
	}
	
}
