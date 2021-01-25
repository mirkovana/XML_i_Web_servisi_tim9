package com.xml.project.rdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.update.UpdateExecutionFactory;
import org.apache.jena.update.UpdateFactory;
import org.apache.jena.update.UpdateProcessor;
import org.apache.jena.update.UpdateRequest;

import com.xml.project.rdf.FusekiAuthenticationUtilities.ConnectionProperties;

public class FusekiWriter {

	public static final String RESPONSE_RDF_FILEPATH = "src/main/resources/rdf/response_metadata.rdf";
	public static final String RESPONSE_METADATA_GRAPH_URI = "/responseMetadata";

	public static final String REQUEST_RDF_FILEPATH = "src/main/resources/rdf/request_metadata.rdf";
	public static final String REQUEST_METADATA_GRAPH_URI = "/requestMetadata";

	public static final String DECISION_APPEAL_RDF_FILEPATH = "src/main/resources/rdf/decision_appeal_metadata.rdf";
	public static final String DECISION_APPEAL_METADATA_GRAPH_URI = "/decisionAppealMetadata";

	public static final String SILENCE_APPEAL_RDF_FILEPATH = "src/main/resources/rdf/silence_appeal_metadata.rdf";
	public static final String SILENCE_APPEAL_METADATA_GRAPH_URI = "/silenceAppealMetadata";
	
	public static final String NOTICE_RDF_FILEPATH = "src/main/resources/rdf/notice_metadata.rdf";
	public static final String NOTICE_METADATA_GRAPH_URI = "/noticeMetadata";

	public static void saveRDF(String RDF_FILEPATH, String METADATA_GRAPH_URI) throws IOException {

		System.out.println("[INFO] Loading triples from an RDF/XML to a model...");
		ConnectionProperties conn = FusekiAuthenticationUtilities.loadProperties();

		// Creates a default model
		Model model = ModelFactory.createDefaultModel();
		model.read(RDF_FILEPATH);

		ByteArrayOutputStream out = new ByteArrayOutputStream();

		model.write(out, SparqlUtil.NTRIPLES);

		System.out.println("[INFO] Rendering model as RDF/XML...");
		model.write(System.out, SparqlUtil.RDF_XML);

		/*
		 * Create UpdateProcessor, an instance of execution of an UpdateRequest.
		 * UpdateProcessor sends update request to a remote SPARQL update service.
		 */

		UpdateRequest request = UpdateFactory.create();

		// TODO Remove this later
		// This will delete all of the triples in all of the named graphs
		// request.add(SparqlUtil.dropAll());
		UpdateProcessor processor = UpdateExecutionFactory.createRemote(request, conn.updateEndpoint);
		processor.execute();

		// Creating the first named graph and updating it with RDF data
		System.out.println("[INFO] Writing the triples to a named graph \"" + METADATA_GRAPH_URI + "\".");

		String sparqlUpdate = SparqlUtil.insertData(conn.dataEndpoint + METADATA_GRAPH_URI,
				new String(out.toByteArray()));
		System.out.println(sparqlUpdate);

		// UpdateRequest represents a unit of execution
		UpdateRequest update = UpdateFactory.create(sparqlUpdate);

		processor = UpdateExecutionFactory.createRemote(update, conn.updateEndpoint);
		processor.execute();
	}
}
