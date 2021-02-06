package com.xml.organvlasti.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestItem{
	@JsonProperty("broj")
	public String broj;
	@JsonProperty("datum")
	public String datum;
	@JsonProperty("institucija")
	public String institucija;
	@JsonProperty("username")
	public String username;
	@JsonProperty("time")
	public String time;
	@JsonProperty("status")
	public String status;
	public RequestItem() {}

	@Override
	public String toString() {
		return "RequestItem [broj=" + broj + ", datum=" + datum + ", institucija=" + institucija + ", username="
				+ username + " time = " + time + " status = " + status +" ]";
	}
	
}