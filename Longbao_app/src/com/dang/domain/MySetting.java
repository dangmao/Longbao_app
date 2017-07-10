package com.dang.domain;

import java.util.ArrayList;

public class MySetting {
	private ArrayList<String> username;
	private ArrayList<String> password;
	private String path;
	public ArrayList<String> getUsername() {
		return username;
	}
	public void setUsername(ArrayList<String> username) {
		this.username = username;
	}
	public ArrayList<String> getPassword() {
		return password;
	}
	public void setPassword(ArrayList<String> password) {
		this.password = password;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
