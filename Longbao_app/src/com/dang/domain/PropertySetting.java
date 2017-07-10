package com.dang.domain;

import javafx.beans.property.SimpleStringProperty;

public class PropertySetting {
	private SimpleStringProperty id = new SimpleStringProperty();
	private SimpleStringProperty username = new SimpleStringProperty();
	private SimpleStringProperty password = new SimpleStringProperty();
	public String getId() {
		return id.get();
	}
	public void setId(String id) {
		this.id.set(id);
	}
	public String getUsername() {
		return username.get();
	}
	public void setUsername(String username) {
		this.username.set(username);
	}
	public String getPassword() {
		return password.get();
	}
	public void setPassword(String password) {
		this.password.set(password); 
	}
	public SimpleStringProperty idProperty()
	{
		return id;
	}
	public SimpleStringProperty usernameProperty()
	{
		return username;
	}
	public SimpleStringProperty passwordProperty()
	{
		return password;
	}

}
