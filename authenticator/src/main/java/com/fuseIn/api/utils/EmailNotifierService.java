package com.fuseIn.api.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

public class EmailNotifierService {

	/*@Autowired
	public JavaMailSender sendEmail;// idhar null a rha hai
	*/
	@Async
	public void SendEmailToUser(SimpleMailMessage message, JavaMailSender sendEmail) {
		sendEmail.send(message);
	}
	
	public String sendTokenVerificationToUser(Map<String, String> map,JavaMailSender sendEmail) {
		
		String tokenUri = Constants.URI+"?token="+map.get("token");
		SimpleMailMessage mailBuilder = new SimpleMailMessage();
		mailBuilder.setTo(map.get("email"));
		mailBuilder.setSubject("Registration confirmation for FuseIn Account");
		mailBuilder.setText(
				"To activate your account, you need to verify your email address...\n Please click on the link to verify "
						+ tokenUri);
		mailBuilder.setFrom("NoReply@fusein.com");
		EmailNotifierService emailService = new EmailNotifierService();
		emailService.SendEmailToUser(mailBuilder,sendEmail);
		return "Verification email sent to you email account";
	}
}
