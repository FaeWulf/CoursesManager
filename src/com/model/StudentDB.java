package com.model;

import java.util.Collection;

public class StudentDB {
	private int id;
	private String mssv;
	private String name;
	private String birthday;
	private String birthPlace;
	private String username;
	private String password;
	private byte sex;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMssv() {
		return mssv;
	}

	public void setMssv(String mssv) {
		this.mssv = mssv;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte getSex() {
		return sex;
	}

	public void setSex(byte sex) {
		this.sex = sex;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StudentDB studentDB = (StudentDB) o;

		if (id != studentDB.id) return false;
		if (sex != studentDB.sex) return false;
		if (mssv != null ? !mssv.equals(studentDB.mssv) : studentDB.mssv != null) return false;
		if (name != null ? !name.equals(studentDB.name) : studentDB.name != null) return false;
		if (birthday != null ? !birthday.equals(studentDB.birthday) : studentDB.birthday != null) return false;
		if (birthPlace != null ? !birthPlace.equals(studentDB.birthPlace) : studentDB.birthPlace != null) return false;
		if (username != null ? !username.equals(studentDB.username) : studentDB.username != null) return false;
		if (password != null ? !password.equals(studentDB.password) : studentDB.password != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (mssv != null ? mssv.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
		result = 31 * result + (birthPlace != null ? birthPlace.hashCode() : 0);
		result = 31 * result + (username != null ? username.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (int) sex;
		return result;
	}

}
