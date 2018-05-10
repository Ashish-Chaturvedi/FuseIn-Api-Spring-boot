package com.fuseIn.api.utils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PropertyUtil {

	private static Logger logger = LogManager.getLogger();

	public String getComponentDetails(String property_key) {

		Properties property = new Properties();
		String propertyFile = "custom.properties";

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFile);

		try {
			if (inputStream != null) {
				property.load(inputStream);
			} else {
				logger.error(new FileNotFoundException("file +" + propertyFile + " not found"));
			}
		} catch (Exception e) {
		}

		String value = property.getProperty(property_key);
		return value;
	}
}
