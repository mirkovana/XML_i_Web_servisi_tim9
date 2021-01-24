package com.xml.organvlasti.parser;

import java.io.File;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

public class JAXParser {
	
	public static  Marshaller createMarshaller(String contextPath, String schemaPath) throws JAXBException, SAXException {
		JAXBContext context = JAXBContext.newInstance(contextPath);
		//SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		//Schema schema = schemaFactory.newSchema(new File(schemaPath));
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		//marshaller.setSchema(schema);
		return marshaller;
	}
	
	public static Unmarshaller createUnmarshaller(String contextPath, String schemaPath) throws JAXBException, SAXException {
		JAXBContext context = JAXBContext.newInstance(contextPath);
		Unmarshaller unmarshaller = context.createUnmarshaller();
		//SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		//Schema schema = schemaFactory.newSchema(new File(schemaPath));
		//unmarshaller.setSchema(schema);
		return unmarshaller;
	}
}
