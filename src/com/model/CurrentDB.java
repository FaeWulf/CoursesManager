package com.model;

public class CurrentDB {
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		CurrentDB currentDB = (CurrentDB) o;

		if (id != currentDB.id) return false;

		return true;
	}

	@Override
	public int hashCode() {
		return id;
	}
}
