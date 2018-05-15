package com.fuseIn.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.datastax.driver.core.Session;
import com.fuseIn.connector.Cassandra;

public class ConnectionsDataSource {
	
	private static Logger logger = LogManager.getLogger();
	
	Session session = null;
	Cassandra cassObj = new Cassandra();
	
	public Session getConnectionFromDatasource() {
		
		
		PropertyUtil proUtil = new PropertyUtil();
		try {
			cassObj.connectDb(proUtil.getComponentDetails("node"));
			session = cassObj.getSession();
	} catch (Exception e) {
		logger.error(e.getMessage());
	}
		return this.session;
	}
	
}
