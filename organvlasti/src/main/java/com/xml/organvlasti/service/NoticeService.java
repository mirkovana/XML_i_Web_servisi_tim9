package com.xml.organvlasti.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.base.XMLDBException;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.xml.organvlasti.model.email.EmailModel;
import com.xml.organvlasti.model.keywordSearch.KeywordSearch;
import com.xml.organvlasti.model.notice.Obavestenje;
import com.xml.organvlasti.model.noticeResponse.NoticeListResponse;
import com.xml.organvlasti.model.noticeResponse.NoticeListResponse.NoticeItem;
import com.xml.organvlasti.model.request.Zahtev;
import com.xml.organvlasti.model.zahtevResponse.RequestListResponse;
import com.xml.organvlasti.model.zahtevResponse.RequestListResponse.RequestItem;
import com.xml.organvlasti.parser.DOMParser;
import com.xml.organvlasti.parser.XSLTransformer;
import com.xml.organvlasti.rdf.FusekiReader;
import com.xml.organvlasti.rdf.FusekiWriter;
import com.xml.organvlasti.rdf.MetadataExtractor;
import com.xml.organvlasti.repository.NoticeRepository;

@Service()
public class NoticeService {

	private final String requestXSL = "src/main/resources/xsl/obavestenje.xsl";

	@Autowired
	private DOMParser domParser;
	@Autowired
	private XSLTransformer xslTransformer;
	@Autowired
	private NoticeRepository repository;
	@Autowired
	private RequestService requestService;
	@Autowired
	private UserService userService;

	@Autowired
	private MetadataExtractor metadataExtractor;

	public void save(String dto) throws ParserConfigurationException, SAXException, IOException, TransformerException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		Document document = domParser.getDocument(dto);
		NodeList br_predmeta = document.getElementsByTagName("ob:broj_predmeta");
		Element el = (Element) br_predmeta.item(0);
		String broj = el.getTextContent(); // broj_predmeta

		Document prev = null;
		try {
			System.out.println("findNoticeById call");
			prev = repository.findNoticeById(broj);
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

		requestService.acceptRequest(broj);

		metadataExtractor.extractMetadata(sw.toString(), MetadataExtractor.NOTICE_RDF_FILE);
		FusekiWriter.saveRDF(FusekiWriter.NOTICE_RDF_FILEPATH, FusekiWriter.NOTICE_METADATA_GRAPH_URI);

		NodeList root = document.getElementsByTagName("ob:obavestenje");
		el = (Element) root.item(0);
		sendEmail(broj, el.getAttribute("username"), el.getAttribute("organVlastiUsername"));
	}

	public NoticeListResponse getAll() throws XMLDBException, JAXBException, SAXException {
		return repository.getAll();
	}

	public NoticeListResponse getAllForOrganVlastiUsername(String username)
			throws XMLDBException, JAXBException, SAXException {
		return repository.getAllForUsername(username, NoticeRepository.X_QUERY_FIND_ALL_BY_ORGANVLASTI_USERNAME);
	}

	public NoticeListResponse getAllForUserUsername(String username)
			throws XMLDBException, JAXBException, SAXException {
		return repository.getAllForUsername(username, NoticeRepository.X_QUERY_FIND_ALL_BY_USER_USERNAME);
	}

	public void deleteNotice(String broj) {
		try {
			repository.deleteRequest(broj);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (XMLDBException e) {
			e.printStackTrace();
		}
	}

	public String getHTML(String broj) {
		Document xml = repository.findNoticeById(broj);
		return xslTransformer.getHTMLfromXML(requestXSL, xml);
	}

	public byte[] getPdfBytes(String broj) throws IOException {
		String html = getHTML(broj);
		/* Setup Source and target I/O streams */
		ByteArrayOutputStream target = new ByteArrayOutputStream();
		/* Setup converter properties. */
		ConverterProperties converterProperties = new ConverterProperties();
		converterProperties.setBaseUri("http://localhost:8080");
		/* Call convert method */
		HtmlConverter.convertToPdf(html, target, converterProperties);
		/* extract output as bytes */
		byte[] bytes = target.toByteArray();

		return bytes;
	}

	public void sendEmail(String broj, String to, String from) {
		try {
			System.out.println("to = " + to + " from = " + from);
			String toEmail = userService.getUserEmailByUsername(to);
			String fromEmail = userService.getUserEmailByUsername(from);
			byte[] pdfBytes = getPdfBytes(broj);

			System.out.println("sendemailnoticeservice");
			String fooResourceUrl = "http://localhost:5000/email";
			RestTemplate restTemplate = new RestTemplate();
			EmailModel email = new EmailModel();
			email.setFrom(fromEmail);
			email.setPdf(Base64.getEncoder().encodeToString(pdfBytes));
			email.setSubject("Obavestenje o zahtevu za pristup informacijama");
			email.setText("Vas zahtev br." + broj + " je prihvacen. U prilogu je dokument sa podacima o datum"
					+ " i vremenu preuzimanja trazenih informaicja.\n" + "html: http://localhost:8080/api/notice/html/"
					+ broj + " \n" + "pdf: http://localhost:8080/api/notice/pdf/" + broj + " \n");
			email.setTo(toEmail);
			System.out.println("emailmodel = " + email);
			HttpEntity<EmailModel> request = new HttpEntity<>(email);
			restTemplate.postForObject(fooResourceUrl, request, EmailModel.class);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public NoticeListResponse search(Map<String, String> params) throws IOException {
		ArrayList<Map<String, String>> result = FusekiReader.executeQuery(params, FusekiReader.NOTICE_QUERY_FILEPATH);
		ArrayList<String> brojeviPredmeta = new ArrayList<>();
		for (Map<String, String> map : result) {
			String obavestenje = map.get("obavestenje");
			String[] split = obavestenje.split("\\/");
			brojeviPredmeta.add(split[split.length - 1]);	
		}
		NoticeListResponse response = new NoticeListResponse();
		List<NoticeItem> itemsList = new ArrayList<>();

		for (String broj : brojeviPredmeta) {
			Obavestenje notice = repository.findNoticeByIdMarshall(broj);
			if (notice == null) {
				continue;
			}

			NoticeItem item = new NoticeItem();
			item.setBroj(notice.getBroj());
			item.setDatum(notice.getDatum());
			item.setImePodnosioca(notice.getOpsteInformacije().getPodaciOPodnosiocu().getIme().getValue());
			item.setPrezimePodnosioca(notice.getOpsteInformacije().getPodaciOPodnosiocu().getPrezime().getValue());
			item.setIznos(Double.toString(notice.getTelo().getIznos()));
			item.setNazivOrgana(notice.getOpsteInformacije().getPodaciOOrganu().getNaziv().getValue());
			item.setOrganVlastiUsername(notice.getOrganVlastiUsername());
			item.setSedisteOrgana(notice.getOpsteInformacije().getPodaciOOrganu().getSediste().getValue());
			item.setUsername(notice.getUsername());
			itemsList.add(item);
		}
		response.setNoticeItem(itemsList);
		return response;
		
	}


	public NoticeListResponse searchByKeywords(KeywordSearch s)
			throws NumberFormatException, XMLDBException, JAXBException, SAXException {
		return repository.searchByKeywords(s);
	}

}
