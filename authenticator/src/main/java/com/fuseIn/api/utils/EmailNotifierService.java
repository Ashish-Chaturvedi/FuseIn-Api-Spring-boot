package com.fuseIn.api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailNotifierService {
	
	//@Autowired
	private JavaMailSender sendEmail;
	
	public void SendEmailToUser(SimpleMailMessage message) {
		sendEmail.send(message);
	}
}
