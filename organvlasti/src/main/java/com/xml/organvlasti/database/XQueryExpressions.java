package com.xml.organvlasti.database;

public class XQueryExpressions {

	public static final String X_QUERY_FIND_ALL_REQUEST = "xquery version \"3.1\";\n" +
            "declare default element namespace \"http://www.projekat.org/zahtev\";\n" +
            "for $x in collection(\"/db/OrganVlasti/requests\")\n" +
            "return $x";
}
