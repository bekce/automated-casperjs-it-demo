package com.sebworks.automated.it.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Selim Eren Bekçe
 */
@Document
public class User {

	@Id
	private String id;
	private String name;
	private String surname;
	private String cellPhone;

	public User(String name, String surname, String cellPhone) {
		this.name = name;
		this.surname = surname;
		this.cellPhone = cellPhone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

}
