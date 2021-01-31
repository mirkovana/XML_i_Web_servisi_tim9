package com.xml.project.service;

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
import com.xml.project.model.reportListItem.ReportListResponse;
import com.xml.project.parser.XSLTransformer;
import com.xml.project.repository.ReportRepository;

@Service()
public class ReportService {

	private final String reportXSL = "src/main/resources/xsl/izvestaj.xsl";
	
	@Autowired
	private ReportRepository repository;
	@Autowired
	private XSLTransformer xslTransformer;
	
	public ReportListResponse getAll() throws XMLDBException, ParserConfigurationException, SAXException, IOException, JAXBException {
		return repository.getAll();
	}

	public String getHTML(String id) {
		Document xml = repository.findReportById(id);
		return xslTransformer.getHTMLfromXML(reportXSL , xml);
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
