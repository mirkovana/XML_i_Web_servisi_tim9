
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package com.spring.soap.ws.hello;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.xml.project.service.DecisionAppealService;
import com.xml.project.service.ResponseService;
import com.xml.project.service.SilenceAppealService;

/**
 * This class was generated by Apache CXF 3.2.1
 * 2020-12-26T14:56:21.123+01:00
 * Generated source version: 3.2.1
 * 
 */

@javax.jws.WebService(
                      serviceName = "SluzbenikService",
                      portName = "SluzbenikPort",
                      targetNamespace = "http://soap.spring.com/ws/sluzbenik",
                     // wsdlLocation = "classpath:wsdl/Sluzbenik.wsdl",
                      endpointInterface = "com.spring.soap.ws.hello.Sluzbenik")
public class SluzbenikPortImpl implements Sluzbenik {

    DecisionAppealService dService;
    SilenceAppealService sService;
    ResponseService responseService;
    
	public SluzbenikPortImpl() {
		dService = new DecisionAppealService();
		sService = new SilenceAppealService();
		responseService = new ResponseService();
	}
	
    public String sayHi(String text) { 
        System.out.println(text);
        try {
        	System.out.println("Inovked sayHi method");
            return "Sluzbenik " + text;
          
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

	@Override
	public String saveDecisionAppeal(String xml) {
		try {
			System.out.println("saveDecisionAppeal");
			dService.save(xml);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "error";
		} catch (InstantiationException e) {
			e.printStackTrace();
			return "error";
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return "error";
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
			return "error";
		} catch (XMLDBException e) {
			e.printStackTrace();
			return "error";
		}
		return "ok";
	}

	@Override
	public String saveSilenceAppeal(String xml) {
		try {
			System.out.println("saveSilenceAppeal");
			sService.save(xml);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "error";
		} catch (InstantiationException e) {
			e.printStackTrace();
			return "error";
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return "error";
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			return "error";
		} catch (SAXException e) {
			e.printStackTrace();
			return "error";
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		} catch (TransformerException e) {
			e.printStackTrace();
			return "error";
		} catch (XMLDBException e) {
			e.printStackTrace();
			return "error";
		}
		return "ok";
	}

	@Override
	public String saveDecisionResponse(String xml) {
		try {
			responseService.saveDecisionResponse(xml);
			return "OK";
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ParserConfigurationException
				| SAXException | IOException | TransformerException | XMLDBException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}

	@Override
	public String saveSilenceResponse(String xml) {
		try {
			responseService.saveSilenceResponse(xml);
			return "OK";
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | ParserConfigurationException
				| SAXException | IOException | TransformerException | XMLDBException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
}