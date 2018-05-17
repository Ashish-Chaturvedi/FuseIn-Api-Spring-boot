package com.fuseIn.api.utils;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

public class EmailNotifierService {

	@Async
	public void SendEmailToUser(SimpleMailMessage message, JavaMailSender sendEmail) {
		sendEmail.send(message);
	}

	public String sendTokenVerificationToUser(Map<String, String> map, JavaMailSender sendEmail) {

		String frontText = "Dear "+ map.get("firstname")+" "+map.get("lastname")+",\n\n"
				+ "Thank you for Registering with us!!!!!!! \n \n"
				+ " To activate your account, you need to verify your email address...\n\n "
				+ "Please click on the link below to verify the same.\n\n";
		String trailText = "\nAbove URL will be valid only for 1 hour. \n"
				+ "\nSincerely,"
				+ "\n\nFuseIn Team";
		String tokenUri = Constants.URI + "?token=" + map.get("token");
		SimpleMailMessage mailBuilder = new SimpleMailMessage();

		mailBuilder.setTo(map.get("email"));
		mailBuilder.setSubject("Email confirmation for FuseIn Account");
		mailBuilder.setText(frontText + tokenUri + trailText);
		mailBuilder.setFrom("fusein.portal@gmail.com");
		EmailNotifierService emailService = new EmailNotifierService();
		emailService.SendEmailToUser(mailBuilder, sendEmail);

		return "Verification email sent to your email account";
	}
}
