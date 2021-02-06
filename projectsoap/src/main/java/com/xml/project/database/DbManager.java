package com.xml.project.database;

import java.io.File;

import javax.xml.transform.OutputKeys;

import org.exist.xmldb.EXistResource;
import org.exist.xupdate.XUpdateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;
import org.xmldb.api.modules.XPathQueryService;
import org.xmldb.api.modules.XUpdateQueryService;

import com.spring.soap.ws.hello.AuthenticationUtilities.ConnectionProperties;

public class DbManager {
	private static final String TARGET_NAMESPACE = "http://www.projekat.org";
	public static final String UPDATE = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
			+ "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:update select=\"%1$s\">%2$s</xu:update>"
			+ "</xu:modifications>";
	public static final String APPEND = "<xu:modifications version=\"1.0\" xmlns:xu=\"" + XUpdateProcessor.XUPDATE_NS
			+ "\" xmlns=\"" + TARGET_NAMESPACE + "\">" + "<xu:append select=\"%1$s\" child=\"last()\">%2$s</xu:append>"
			+ "</xu:modifications>";

	ConnectionProperties conn;
	
	public DbManager() {}
	
	public DbManager(ConnectionProperties conn) {
		this.conn = conn;
	}
	
	public void setAuthUtil(ConnectionProperties conn) {
		this.conn = conn;
	}

	public void createConnection()
			throws ClassNotFoundException, XMLDBException, InstantiationException, IllegalAccessException {
		Class<?> cl = Class.forName(conn.getDriver());
		Database database = (Database) cl.newInstance();
		database.setProperty("create-database", "true");
		DatabaseManager.registerDatabase(database);
	}

	public void closeConnection(Collection col, XMLResource res) {
		if (res != null) {
			try {
				((EXistResource) res).freeResources();
			} catch (XMLDBException xe) {
				xe.printStackTrace();
			}
		}

		if (col != null) {
			try {
				col.close();
			} catch (XMLDBException xe) {
				xe.printStackTrace();
			}
		}
	}

	public void store(String collectionId, String documentId, String filePath)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		createConnection();

		Collection col = null;
		XMLResource res = null;
		try {

			System.out.println("[INFO] Retrieving the collection: " + collectionId);
			col = getOrCreateCollection(collectionId, 0);
			System.out.println("[INFO] Inserting the document: " + documentId);
			res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
			File f = new File(filePath);
			if (!f.canRead()) {
				System.out.println("[ERROR] Cannot read the file: " + filePath);
				return;
			}
			res.setContent(f);
			System.out.println("[INFO] Storing the document: " + res.getId());
			col.storeResource(res);
			System.out.println("[INFO] Done.");
		} finally {
			closeConnection(col, res);
		}
	}

	public ResourceSet retrieve(String collectionUri, String xpathExp)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		createConnection();
		Collection col = null;
		ResourceSet result = null;
		try {
			col = DatabaseManager.getCollection(conn.getUri() + collectionUri);
			XPathQueryService xpathService = (XPathQueryService) col.getService("XPathQueryService", "1.0");
			xpathService.setProperty("indent", "yes");
			xpathService.setNamespace("", TARGET_NAMESPACE);
			System.out.println("[INFO] Invoking XPath query service for: " + xpathExp);
			result = xpathService.query(xpathExp);
			System.out.println("[INFO] Done! ");

		} finally {
			if (col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}

		return result;
	}

	public XMLResource getDocument(String collectionUri, String documentId)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		createConnection();
		Collection col = null;
		XMLResource res = null;
		try {
			col = DatabaseManager.getCollection(conn.getUri() + collectionUri);
			System.out.println("col = " + col);
			col.setProperty(OutputKeys.INDENT, "yes");
			System.out.println("[INFO] Retrieving the document: " + documentId);
			res = (XMLResource) col.getResource(documentId);
			if (res == null) {
				System.out.println("[WARNING] Document '" + documentId + "' can not be found!");
			} else {
				System.out.println("[Done]");
			}
			return res;
		} finally {
			if (col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
	}

	public void deleteDocument(String collectionId, String documentId)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, XMLDBException {
		createConnection();

		Collection col = null;
		try {
			System.out.println("db manager deleteDocument = " + (conn.getUri() + collectionId));
			col = DatabaseManager.getCollection(conn.getUri() + collectionId, conn.getUser(),
					conn.getPassword());
			Resource foundFile = col.getResource(documentId);
			col.removeResource(foundFile);
		} finally {
			if (col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
	}

	public void updateDocument(int template, String collectionId, String documentId, String contextXPath, String patch)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		createConnection();
		Collection col = null;
		String chosenTemplate = null;
		switch (template) {
		case 0:
			chosenTemplate = UPDATE;
			break;
		case 1:
			chosenTemplate = APPEND;
			break;
		default:
			return;
		}
		try {
			System.out.println("[INFO] Retrieving the collection: " + collectionId);
			col = DatabaseManager.getCollection(conn.getUri() + collectionId, conn.getUser(),
					conn.getPassword());
			col.setProperty("indent", "yes");
			System.out.println("[INFO] Fetching XUpdate service for the collection.");
			XUpdateQueryService xupdateService = (XUpdateQueryService) col.getService("XUpdateQueryService", "1.0");
			xupdateService.setProperty("indent", "yes");
			System.out.println("[INFO] Updating " + contextXPath + " node.");
			long mods = xupdateService.updateResource(documentId, String.format(chosenTemplate, contextXPath, patch));
			System.out.println("[INFO] " + mods + " modifications processed.");
			System.out.println("[TEMPLATE] " + chosenTemplate);

		} finally {
			if (col != null) {
				try {
					col.close();
				} catch (XMLDBException xe) {
					xe.printStackTrace();
				}
			}
		}
	}

	public Collection getCollection(String collectionUri) throws XMLDBException {
		return getOrCreateCollection(collectionUri, 0);
	}
	 
	private Collection getOrCreateCollection(String collectionUri, int pathSegmentOffset) throws XMLDBException {
		Collection col = DatabaseManager.getCollection(conn.getUri() + collectionUri, conn.getUser(),
				conn.getPassword());
		if (col == null) {
			if (collectionUri.startsWith("/")) {
				collectionUri = collectionUri.substring(1);
			}
			String pathSegments[] = collectionUri.split("/");
			if (pathSegments.length > 0) {
				StringBuilder path = new StringBuilder();
				for (int i = 0; i <= pathSegmentOffset; i++) {
					path.append("/" + pathSegments[i]);
				}
				Collection startCol = DatabaseManager.getCollection(conn.getUri() + path, conn.getUser(),
						conn.getPassword());
				if (startCol == null) {
					String parentPath = path.substring(0, path.lastIndexOf("/"));
					Collection parentCol = DatabaseManager.getCollection(conn.getUri() + parentPath,
							conn.getUser(), conn.getPassword());
					CollectionManagementService mgt = (CollectionManagementService) parentCol
							.getService("CollectionManagementService", "1.0");
					System.out.println("[INFO] Creating the collection: " + pathSegments[pathSegmentOffset]);
					col = mgt.createCollection(pathSegments[pathSegmentOffset]);
					col.close();
					parentCol.close();
				} else {
					startCol.close();
				}
			}
			return getOrCreateCollection(collectionUri, ++pathSegmentOffset);
		} else {
			return col;
		}
	}
	public void storeXMLFromText(String collectionId, String documentId, String xmlContent)
			throws XMLDBException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		createConnection();
		Collection col = null;
		XMLResource res = null;
		try {
			col = getOrCreateCollection(collectionId, 0);
			res = (XMLResource) col.createResource(documentId, XMLResource.RESOURCE_TYPE);
			res.setContent(xmlContent);
			col.storeResource(res);
		} finally {
			closeConnection(col, res);
		}
	}
}
