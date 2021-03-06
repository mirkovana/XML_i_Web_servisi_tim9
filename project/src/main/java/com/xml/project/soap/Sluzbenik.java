package com.xml.project.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

/**
 * This class was generated by Apache CXF 3.2.1
 * 2020-12-26T14:56:21.140+01:00
 * Generated source version: 3.2.1
 * 
 */
@WebService(targetNamespace = "http://soap.spring.com/ws/sluzbenik", name = "Sluzbenik")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface Sluzbenik {

    @WebMethod
    @WebResult(name = "return", targetNamespace = "http://soap.spring.com/ws/sluzbenik", partName = "return")
    public String sayHi(
        @WebParam(partName = "text", name = "text")
        String text
    );
   
    @WebMethod
    @WebResult(name = "return", targetNamespace = "http://soap.spring.com/ws/sluzbenik", partName = "return")
    public String saveDecisionAppeal(
        @WebParam(partName = "xml", name = "xml")
        String xml
    );
    
    @WebMethod
    @WebResult(name = "return", targetNamespace = "http://soap.spring.com/ws/sluzbenik", partName = "return")
    public String saveSilenceAppeal(
        @WebParam(partName = "xml", name = "xml")
        String xml
    );
    
    @WebMethod
    @WebResult(name = "return", targetNamespace = "http://soap.spring.com/ws/sluzbenik", partName = "return")
    public String saveDecisionResponse(
        @WebParam(partName = "xml", name = "xml")
        String xml
    );
    
    @WebMethod
    @WebResult(name = "return", targetNamespace = "http://soap.spring.com/ws/sluzbenik", partName = "return")
    public String saveSilenceResponse(
        @WebParam(partName = "xml", name = "xml")
        String xml
    );
}
