package com.xml.project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DecisionAppealDTO {
	
	@JsonProperty("text")
	private String text;

	public DecisionAppealDTO() {
		
	}
	
	public DecisionAppealDTO(String text) {
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
		return "AppealDecisionDTO [text=" + text + "]";
	}
}
