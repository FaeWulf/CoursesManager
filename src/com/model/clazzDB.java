package com.model;

public class clazzDB {
	private String id;
	private int size;
	private int male;
	private int female;
	private int year;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMale() {
		return male;
	}

	public void setMale(int male) {
		this.male = male;
	}

	public int getFemale() {
		return female;
	}

	public void setFemale(int female) {
		this.female = female;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		clazzDB clazzDB = (com.model.clazzDB) o;

		if (size != clazzDB.size) return false;
		if (male != clazzDB.male) return false;
		if (female != clazzDB.female) return false;
		if (year != clazzDB.year) return false;
		if (id != null ? !id.equals(clazzDB.id) : clazzDB.id != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + size;
		result = 31 * result + male;
		result = 31 * result + female;
		result = 31 * result + year;
		return result;
	}
}
