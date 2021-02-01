package com.xml.project.rdf;

import java.io.IOException;
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

import com.xml.project.rdf.FusekiAuthenticationUtilities.ConnectionProperties;


public class FusekiReader {
	
	public static final String RESPONSE_QUERY_FILEPATH = "src/main/resources/rdf/responseQuery.rq";
	public static final String REQUEST_QUERY_FILEPATH = "src/main/resources/rdf/requestQuery.rq";
	public static final String DECISION_APPEAL_QUERY_FILEPATH = "src/main/resources/rdf/decisionAppealQuery.rq";
	public static final String SILENCE_APPEAL_QUERY_FILEPATH = "src/main/resources/rdf/silenceAppealQuery.rq";
	
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
}
