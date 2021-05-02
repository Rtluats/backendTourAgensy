package com.spring.multimodule.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailService {
	private static final String PATH = "src/resources/Template.odt";
	private final JavaMailSender emailSender;

	public MailService(JavaMailSender emailSender) {
		this.emailSender = emailSender;
	}

	public void sendDocToEmail(String toEmail, String title) throws MessagingException {

		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setFrom("rtluats@gmail.com");
		helper.setTo(toEmail);
		helper.setSubject("Tour to " + title);
		helper.setText("fill out the contract and call us by phone +37544123457");
		FileSystemResource file = new FileSystemResource(new File(PATH));
		helper.addAttachment("Contract", file);
		emailSender.send(message);
	}

}
