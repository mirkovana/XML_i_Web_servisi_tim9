package com.xml.organvlasti.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NoticeDTO {

	@JsonProperty("text")
	private String text;

	public NoticeDTO() {

	}

	public NoticeDTO(String text) {
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
		return "NoticeDTO [text=" + text + "]";
	}

}
