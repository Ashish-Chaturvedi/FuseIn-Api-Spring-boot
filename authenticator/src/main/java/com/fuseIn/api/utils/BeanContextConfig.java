package com.fuseIn.api.utils;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

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
	public User user() {
		return new User();
	}
	
	/*@Bean
	public JavaMailSenderImpl sendEmail() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	     
	    mailSender.setUsername("fusein.portal@gmail.com");
	    mailSender.setPassword("Ch@mp10n92");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}*/
}
