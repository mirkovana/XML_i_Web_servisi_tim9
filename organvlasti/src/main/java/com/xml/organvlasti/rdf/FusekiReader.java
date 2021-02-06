package com.xml.organvlasti.rdf;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.RDFNode;

import com.xml.organvlasti.rdf.FusekiAuthenticationUtilities.ConnectionProperties;


public class FusekiReader {
	
	public static final String RESPONSE_QUERY_FILEPATH = "src/main/resources/rdf/responseQuery.rq";
	public static final String REQUEST_QUERY_FILEPATH = "src/main/resources/rdf/requestQuery.rq";
	public static final String DECISION_APPEAL_QUERY_FILEPATH = "src/main/resources/rdf/decisionAppealQuery.rq";
	public static final String SILENCE_APPEAL_QUERY_FILEPATH = "src/main/resources/rdf/silenceAppealQuery.rq";
	public static final String NOTICE_QUERY_FILEPATH = "src/main/resources/rdf/noticeQuery.rq";

	private FusekiReader() {}

	public static ArrayList<Map<String, String>> executeQuery(Map<String, String> params, String QUERY_FILEPATH) throws IOException {
		
		ConnectionProperties conn = FusekiAuthenticationUtilities.loadProperties();
		
		String sparqlQueryTemplate = readFile(QUERY_FILEPATH, StandardCharsets.UTF_8);
		System.out.println("Query: " + sparqlQueryTemplate);
		String sparqlQuery = StringSubstitutor.replace(sparqlQueryTemplate, params, "{{", "}}");
		System.out.println("Query: " + sparqlQuery);
		
		// Create a QueryExecution that will access a SPARQL service over HTTP
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
		// Query the SPARQL endpoint, iterate over the result set...
		ResultSet results = query.execSelect();
		System.out.println("results = " + results);
		String varName;
		RDFNode varValue;
		ArrayList<Map<String, String>> returnList = new ArrayList<>();
		
		while(results.hasNext()){
			System.out.println("results.hasNext()");
            // A single answer from a SELECT query
			QuerySolution querySolution = results.next() ;
			Iterator<String> variableBindings = querySolution.varNames();
			
			Map<String, String> itemsMap = new HashMap<>();
		    while (variableBindings.hasNext()) {
		   
		    	varName = variableBindings.next();
		    	varValue = querySolution.get(varName);
		    	
		    	String[] v = varValue.toString().split("\\^");
		    	System.out.println(varName + ": " + varValue.toString());
		    	if(v.length > 0) {
			    	itemsMap.put(varName, v[0]);		    		
		    	}
		    }
		    System.out.println();
		    returnList.add(itemsMap);
        }
		
	    ResultSetFormatter.outputAsXML(System.out, results);
		query.close() ;
		System.out.println("[INFO] SPARQL Query End.");
		return returnList;
	}
	
	public static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
	
	public static final String RESPONSE_METADATA_GRAPH_URI = "/responseMetadata";
	public static final String REQUEST_METADATA_GRAPH_URI = "/requestMetadata";
	public static final String DECISION_APPEAL_METADATA_GRAPH_URI = "/decisionAppealMetadata";
	public static final String SILENCE_APPEAL_METADATA_GRAPH_URI = "/silenceAppealMetadata";	
	public static final String NOTICE_METADATA_GRAPH_URI = "/noticeMetadata";

	public static void generateDAppealJSON(String broj) throws IOException {
		ConnectionProperties conn = FusekiAuthenticationUtilities.loadProperties();

		String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + 
				DECISION_APPEAL_METADATA_GRAPH_URI, "<http://www.projekat.org/zalbanaodluku/" + broj + "> ?p ?o");
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
		ResultSet results = query.execSelect();
		String jsonPath = "src/main/resources/json/zalbanaodluku_" + broj + ".json";
		File rdfFile = new File(jsonPath);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(rdfFile));
		ResultSetFormatter.outputAsJSON(out, results);
		query.close();
	}
	
	public static void generateSAppealJSON(String broj) throws IOException {
		ConnectionProperties conn = FusekiAuthenticationUtilities.loadProperties();

		String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + 
				SILENCE_APPEAL_METADATA_GRAPH_URI, "<http://www.projekat.org/zalbazbogcutanja/" + broj + "> ?p ?o");
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
		ResultSet results = query.execSelect();
		String jsonPath = "src/main/resources/json/zalbazbogcutanja_" + broj + ".json";
		File rdfFile = new File(jsonPath);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(rdfFile));
		ResultSetFormatter.outputAsJSON(out, results);
		query.close();
	}
	
	public static void generateRequestJSON(String broj) throws IOException {
		ConnectionProperties conn = FusekiAuthenticationUtilities.loadProperties();

		String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + 
				REQUEST_METADATA_GRAPH_URI, "<http://www.projekat.org/zahtev/" + broj + "> ?p ?o");
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
		ResultSet results = query.execSelect();
		String jsonPath = "src/main/resources/json/zahtev_" + broj + ".json";
		File rdfFile = new File(jsonPath);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(rdfFile));
		ResultSetFormatter.outputAsJSON(out, results);
		query.close();
	}
	
	public static void generateNoticeJSON(String broj) throws IOException {
		ConnectionProperties conn = FusekiAuthenticationUtilities.loadProperties();

		String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + 
				NOTICE_METADATA_GRAPH_URI, "<http://www.projekat.org/obavestenje/" + broj + "> ?p ?o");
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
		ResultSet results = query.execSelect();
		String jsonPath = "src/main/resources/json/obavestenje_" + broj + ".json";
		File rdfFile = new File(jsonPath);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(rdfFile));
		ResultSetFormatter.outputAsJSON(out, results);
		query.close();
	}
	
	public static void generateResponseJSON(String broj) throws IOException {
		ConnectionProperties conn = FusekiAuthenticationUtilities.loadProperties();

		String sparqlQuery = SparqlUtil.selectData(conn.dataEndpoint + 
				RESPONSE_METADATA_GRAPH_URI, "<http://www.projekat.org/resenje/" + broj + "> ?p ?o");
		QueryExecution query = QueryExecutionFactory.sparqlService(conn.queryEndpoint, sparqlQuery);
		ResultSet results = query.execSelect();
		String jsonPath = "src/main/resources/json/resenje_" + broj + ".json";
		File rdfFile = new File(jsonPath);
		OutputStream out = new BufferedOutputStream(new FileOutputStream(rdfFile));
		ResultSetFormatter.outputAsJSON(out, results);
		query.close();
	}
}
