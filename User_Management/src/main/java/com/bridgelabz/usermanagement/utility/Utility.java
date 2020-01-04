package com.bridgelabz.usermanagement.utility;

import java.time.LocalDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
@Component
public class Utility {
	@Autowired
	TokenUtility tokenUtility;
	@Autowired
    JavaMailSender javaMailSender;
	
	@Bean
	public ModelMapper getMapper() {
		return new ModelMapper();
	}

	public static LocalDateTime CurrentDateTime() {
		LocalDateTime currentTime = LocalDateTime.now();

		return currentTime;

	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public String sendNotification(String link) {
		String mail=TokenUtility.generateUserFromToken(link);
		SimpleMailMessage simple = new SimpleMailMessage();
		simple.setTo(mail);
		System.out.println(mail);
		simple.setSubject("Verify your account:");
		simple.setText(CommanLogFile.default_message + link);	
		javaMailSender.send(simple);
		return CommanLogFile.default_message;
	}
}
