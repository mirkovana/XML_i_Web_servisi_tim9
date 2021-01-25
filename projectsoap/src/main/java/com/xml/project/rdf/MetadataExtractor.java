package com.xml.project.rdf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;


/**
 * 
 * Primer demonstrira ekstrakciju RDFa metapodataka iz 
 * XML dokumenta primenom GRDDL (Gleaning Resource Descriptions 
 * from Dialects of Languages) transformacije.
 * 
 */
@Component
public class MetadataExtractor {
	
	private TransformerFactory transformerFactory;

	private static final String XSLT_FILE = "src/main/resources/xsl/grddl.xsl";
	
	public static final String RESPONSE_RDF_FILE = "src/main/resources/rdf/response_metadata.rdf";
	public static final String REQUEST_RDF_FILE = "src/main/resources/rdf/request_metadata.rdf";
	public static final String DECISION_APPEAL_RDF_FILE = "src/main/resources/rdf/decision_appeal_metadata.rdf";
	public static final String SILENCE_APPEAL_RDF_FILE = "src/main/resources/rdf/silence_appeal_metadata.rdf";
	public static final String NOTICE_RDF_FILE = "src/main/resources/rdf/notice_metadata.rdf";

	public MetadataExtractor() throws SAXException, IOException {
		
		// Setup the XSLT transformer factory
		transformerFactory = new TransformerFactoryImpl();
	}

	/**
	 * Generates RDF/XML based on RDFa metadata from an XML containing 
	 * input stream by applying GRDDL XSL transformation.
	 *  
	 * @param in XML containing input stream
	 * @param out RDF/XML output stream
	 */
	public void extractMetadata(String xmlString, String RDF_FILE) throws FileNotFoundException, TransformerException {
		
		OutputStream out = new FileOutputStream(new File(RDF_FILE));

		// Create transformation source
		StreamSource transformSource = new StreamSource(new File(XSLT_FILE));
		// Initialize GRDDL transformer object
		Transformer grddlTransformer = transformerFactory.newTransformer(transformSource);
		// Set the indentation properties
		grddlTransformer.setOutputProperty("{http://xml.apache.org/xalan}indent-amount", "2");
		grddlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		
		// Initialize result stream
		StreamResult result = new StreamResult(out);
		// Initialize input stream
		StreamSource source = new StreamSource(new StringReader(xmlString));
		// Trigger the transformation
		grddlTransformer.transform(source, result);
		
	}

}
