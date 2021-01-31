package com.xml.organvlasti.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.xml.organvlasti.model.decisionAppealResponse.DAppealListResponse;
import com.xml.organvlasti.model.report.Izvestaj;
import com.xml.organvlasti.model.report.Izvestaj.Zahtevi;
import com.xml.organvlasti.model.report.Izvestaj.Zalbe;
import com.xml.organvlasti.model.reportListItem.ReportListResponse;
import com.xml.organvlasti.model.silenceAppealResponse.SAppealListResponse;
import com.xml.organvlasti.model.zahtevResponse.RequestListResponse;
import com.xml.organvlasti.model.zahtevResponse.RequestListResponse.RequestItem;
import com.xml.organvlasti.parser.JAXParser;
import com.xml.organvlasti.parser.XSLTransformer;
import com.xml.organvlasti.repository.ReportRepository;

@Service()
public class ReportService {

	private final String reportXSL = "src/main/resources/xsl/izvestaj.xsl";
	private static String schemaPath = "src/main/resources/documents/izvestaj.xsd";
	private static String contextPath = "com.xml.organvlasti.model.report";
	
	@Autowired
	private ReportRepository repository;
	@Autowired
	private RequestService requestService;
	@Autowired
	private DecisionAppealService dAppealService;
	@Autowired
	private SilenceAppealService sAppealService;
	@Autowired
	private XSLTransformer xslTransformer;
	
	public Izvestaj generateReport() throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("generatereport service");

		RequestListResponse requests = requestService.getAll();
		DAppealListResponse dAppeals = dAppealService.getAll();
		SAppealListResponse sAppeals = sAppealService.getAll();
		
		Izvestaj izvestaj = new Izvestaj();
		
		Zahtevi zahtevi = new Zahtevi();
		zahtevi.setBrojPodnetih(requests.getRequestItem().size());
		int odbaceni = 0, odbijeni = 0, usvojeni = 0;
		for(RequestItem item : requests.getRequestItem()) {
			if(item.getStatus().contentEquals("denied")) {
				odbijeni++;
			}else if (item.getStatus().contentEquals("expired")) {
				odbaceni++;
			}else if(item.getStatus().contentEquals("accepted")) {
				usvojeni++;
			}
		}
		zahtevi.setBrojOdbacenih(odbaceni);
		zahtevi.setBrojOdbijenih(odbijeni);
		zahtevi.setBrojUsvojenih(usvojeni);
		
		Zalbe zalbe = new Zalbe();
		zalbe.setBrojPodnetih(dAppeals.getDAppealItem().size() + sAppeals.getSAppealItem().size());
		zalbe.setBrojZbogNepostupanja(sAppeals.getSAppealItem().size());
		zalbe.setBrojZbogOdbijanja(dAppeals.getDAppealItem().size());
		zalbe.setBrojNaZakljucakOdbacivanju(0);
		
		izvestaj.setZahtevi(zahtevi);
		izvestaj.setZalbe(zalbe);
		
		saveReport(izvestaj);
		return izvestaj;
	}
	
	public void saveReport(Izvestaj izvestaj) throws JAXBException, SAXException, ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save izvestaj = " + izvestaj);
		String id = UUID.randomUUID().toString().split("-")[4];
		izvestaj.setId(id);
		SimpleDateFormat sf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date date = new Date(Long.parseLong(Long.toString(System.currentTimeMillis())));		
		izvestaj.setDatum(sf.format(date));
		
		Marshaller marshaller = JAXParser.createMarshaller(contextPath, schemaPath);
		StringWriter sw = new StringWriter();
		marshaller.marshal(izvestaj, sw);
		String xmlString = sw.toString();
		System.out.println("xmlstirng = " + xmlString);
		repository.save(xmlString, id);
	}

	public String getHTML(String id) {
		Document xml = repository.findReportById(id);
		return xslTransformer.getHTMLfromXML(reportXSL , xml);
	}

	public ReportListResponse getAll() throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException {
		return repository.getAll();
	}

	public byte[] getPdfBytes(String broj) throws IOException {
		String html = getHTML(broj);
        /* Setup Source and target I/O streams */
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        /*Setup converter properties. */
        ConverterProperties converterProperties = new ConverterProperties();
        converterProperties.setBaseUri("http://localhost:8080");
        /* Call convert method */
        HtmlConverter.convertToPdf(html, target, converterProperties);  
        /* extract output as bytes */
        byte[] bytes = target.toByteArray();

        return bytes;
	}
}
