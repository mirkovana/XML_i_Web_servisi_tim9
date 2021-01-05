package com.xml.project.controller;

import java.io.IOException;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmldb.api.base.XMLDBException;

import com.xml.project.database.DbManager;

@RestController
@RequestMapping("/api/db")
@CrossOrigin
public class DbController {
	@Autowired
	public DbManager dbManager;

	@GetMapping(value = "/init")
	public void init()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException, IOException {
		System.out.println("initiate data");
		Resource resource = new ClassPathResource("data");
		dbManager.store("/db/XmlProject/users", "Users.xml", resource.getURI().getPath() + "/Users.xml");
	}
}
