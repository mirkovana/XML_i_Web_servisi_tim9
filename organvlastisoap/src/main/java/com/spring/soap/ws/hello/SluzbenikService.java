package com.spring.soap.ws.hello;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 3.2.1
 * 2020-12-26T14:56:21.149+01:00
 * Generated source version: 3.2.1
 * 
 */
@WebServiceClient(name = "SluzbenikService", 
                  wsdlLocation = "classpath:wsdl/Sluzbenik.wsdl",
                  targetNamespace = "http://soap.spring.com/ws/sluzbenik") 
public class SluzbenikService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikService");
    public final static QName SluzbenikPort = new QName("http://soap.spring.com/ws/sluzbenik", "SluzbenikPort");
    static {
        URL url = SluzbenikService.class.getClassLoader().getResource("wsdl/Sluzbenik.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(SluzbenikService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/Sluzbenik.wsdl");
        }       
        WSDL_LOCATION = url;   
    }

    public SluzbenikService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public SluzbenikService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SluzbenikService() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    public SluzbenikService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public SluzbenikService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public SluzbenikService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }    




    /**
     *
     * @return
     *     returns Sluzbenik
     */
    @WebEndpoint(name = "SluzbenikPort")
    public Sluzbenik getSluzbenikPort() {
        return super.getPort(SluzbenikPort, Sluzbenik.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns Sluzbenik
     */
    @WebEndpoint(name = "SluzbenikPort")
    public Sluzbenik getSluzbenikPort(WebServiceFeature... features) {
        return super.getPort(SluzbenikPort, Sluzbenik.class, features);
    }

}
