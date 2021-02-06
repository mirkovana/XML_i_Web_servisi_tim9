package com.xml.organvlasti.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseDTO {
	
	@JsonProperty("text")
	private String text;

	public ResponseDTO() {
		
	}
	
	public ResponseDTO(String text) {
		super();
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "ResponseDTO [text=" + text + "]";
	}
	
}
