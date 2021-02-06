package com.spring.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.spring.email.model.EmailModel;

import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.mail.SimpleMailMessage;

import java.util.Base64;
import java.util.Base64.Decoder;

import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.MimeMessageHelper;


@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;

	/*@Async
	public void sendMail(EmailModel email){
		System.out.println("sendMail in service = " + email);
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(email.getTo());
		mail.setFrom(email.getFrom());
		mail.setSubject(email.getSubject());
		mail.setText(email.getText());
		javaMailSender.send(mail);
	}*/
	
	@Async
	public void sendMailPdf(EmailModel email) {
		MimeMessage message = javaMailSender.createMimeMessage();
	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	        helper.setFrom(email.getFrom());
	        helper.setTo(email.getTo());
	        helper.setSubject(email.getSubject());
	        helper.setText(email.getText());
	        Decoder decoder = Base64.getDecoder();
	        byte[] bytes = decoder.decode(email.getPdf());
	        helper.addAttachment("file.pdf", new ByteArrayResource(bytes));
	        javaMailSender.send(message);
	    } catch (MessagingException e) {

	        e.printStackTrace();
	    }
	}

}