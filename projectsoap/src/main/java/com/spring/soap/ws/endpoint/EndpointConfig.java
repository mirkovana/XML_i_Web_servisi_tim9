package com.spring.soap.ws.endpoint;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spring.soap.ws.hello.PoverenikPortImpl;

@Configuration
public class EndpointConfig {

	@Autowired
	private Bus bus;
	
	@Bean
	public Endpoint poverenikEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, new PoverenikPortImpl());
		endpoint.publish("/poverenik");
		return endpoint;
	}
	
}
