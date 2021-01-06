package com.xml.project.service;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.xml.project.dto.ResponseDTO;
import com.xml.project.parser.DOMParser;
import com.xml.project.parser.XSLTransformer;
import com.xml.project.repository.ResponseRepository;

@Service()
public class ResponseService {

	private final String responseXSL = "src/main/resources/xsl/resenje.xsl";
	private final String responseXSL1 = "src/main/resources/xsl/obavestenje.xsl";

	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private ResponseRepository repository;

	public void save(ResponseDTO dto)
			throws ParserConfigurationException, SAXException, IOException, TransformerException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto.getText());
		System.out.println("got document = " + document);
		NodeList nodeList = document.getElementsByTagName("res:zalba");
		Element sp = (Element) nodeList.item(0);
		String broj = sp.getAttribute("broj");
		System.out.println("node broj = " + broj);
		broj = broj.replace("/", "_");
		Document prev = null;
		try {
			System.out.println("findResponseByBroj call");
			prev = repository.findResponseByBroj(broj);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("response already exist");
			return;
		}
		System.out.println("found response = " + prev);

		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

		transformer.transform(new DOMSource(document), new StreamResult(sw));

		System.out.println("broj after = " + broj);
		repository.save(sw.toString(), broj + ".xml");

	}

	public void saveObavestenje(ResponseDTO dto)
			throws ParserConfigurationException, SAXException, IOException, TransformerException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
//		System.out.println("save service = " + dto);
		Document document = domParser.getDocument(dto.getText());
//		System.out.println("got document = " + document);

		NodeList br_predmeta = document.getElementsByTagName("ob:broj_predmeta");

		Element el = (Element) br_predmeta.item(0);

		String broj = el.getTextContent(); // broj_predmeta

		Document prev = null;
		try {
			System.out.println("findResponseByBroj call");
			prev = repository.findResponseByBroj(broj);
		} catch (Exception e) {
			System.out.println("exception = " + e.getMessage());
		}
		if (prev != null) {
			System.out.println("response already exist");
			return;
		}
		System.out.println("found response = " + prev);
		
		StringWriter sw = new StringWriter();
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		
		System.out.println("broj after = " + broj);
		repository.save(sw.toString(), broj + ".xml");

	}

	public String getHTML(String broj) {
		Document xml = repository.findResponseByBroj(broj);
		System.out.println(xml);
		return xslTransformer.getHTMLfromXML(responseXSL1, xml);
	}

	/*
	 * public Resource getPdf(String name) throws Exception { Document document =
	 * repository.findResponseByBroj(name); StringWriter sw = new StringWriter();
	 * TransformerFactory tf = TransformerFactory.newInstance(); Transformer
	 * transformer = tf.newTransformer();
	 * transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	 * transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	 * transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	 * transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	 * 
	 * transformer.transform(new DOMSource(document), new StreamResult(sw));
	 * ByteArrayOutputStream outputStream =
	 * xslTransformer.generatePDf(sw.toString(), xslFOPath);
	 * 
	 * Path file = Paths.get(name + ".pdf"); Files.write(file,
	 * outputStream.toByteArray());
	 * 
	 * return new UrlResource(file.toUri()); }
	 */
}
