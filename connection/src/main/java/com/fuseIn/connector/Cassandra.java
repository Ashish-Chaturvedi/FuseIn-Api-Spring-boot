package com.fuseIn.connector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

public class Cassandra {
	
	private static Logger logger = LogManager.getLogger();
	
	private Cluster cluster;
	
	private Session session;	
	
	public void connectDb(String node) {
		
		try {
		this.cluster = Cluster.builder().addContactPoint(node).withPort(9042).build();
		Metadata metadata = cluster.getMetadata();
		logger.info("Connected to cluster: "+ metadata.getClusterName());
		}
		catch(Exception e) {
}
		session = cluster.connect(); 
	}
	
	public Session getSession() {
		return this.session;
	}
	
	public void closeSession() {
		cluster.close();
	}
}
