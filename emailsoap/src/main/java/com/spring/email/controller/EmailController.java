package com.spring.email.controller;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.spring.email.model.EmailModel;
import com.spring.email.service.EmailService;

@RestController
@RequestMapping(value = "/email", produces = MediaType.APPLICATION_XML_VALUE)
public class EmailController {

	@Autowired
    EmailService emailService;
	
	@RequestMapping(method= RequestMethod.POST)
    public ResponseEntity<?> sendEmail(@RequestBody EmailModel email) {
		System.out.println("send email EmailController = " + email);
        emailService.sendMailPdf(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}