package com.xml.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SilenceAppealDTO {
	
	@JsonProperty("text")
	private String text;

	public SilenceAppealDTO() {
		
	}
	
	public SilenceAppealDTO(String text) {
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
		return "AppealSilenceDTO [text=" + text + "]";
	}
}
