package com.xml.organvlasti.dto;

public class SaveDTO {
	private String collectionId;
	private String name;
	private String path;

	public SaveDTO() {
	}

	public SaveDTO(String collectionId, String name, String path) {
		this.collectionId = collectionId;
		this.name = name;
		this.path = path;
	}

	public String getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
