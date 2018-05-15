package com.fuseIn.api.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fuseIn.api.Impl.RegistrationServiceImpl;
import com.fuseIn.api.Interface.IRegister;
import com.fuseIn.api.Interface.IRegisterDao;
import com.fuseIn.api.daoImpl.RegistrationDaoImpl;
import com.fuseIn.api.entity.User;
import com.fuseIn.connector.Cassandra;

@Configuration
public class BeanContextConfig {

	@Bean
	public IRegisterDao registerUserDao() {
		return new RegistrationDaoImpl();
	}
	@Bean
	public IRegister registerUserBo() {
		return new RegistrationServiceImpl(registerUserDao());
	}
	@Bean
	public Cassandra cassObj() {
		return new Cassandra();
	}
	@Bean
	public User user() {
		return new User();
	}
	
}
