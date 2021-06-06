package com.model;

import java.io.Serializable;

public class KydkhpDBPK implements Serializable {
	private int hpId;
	private String semesterId;
	private int year;

	public int getHpId() {
		return hpId;
	}

	public void setHpId(int hpId) {
		this.hpId = hpId;
	}

	public String getSemesterId() {
		return semesterId;
	}

	public void setSemesterId(String semesterId) {
		this.semesterId = semesterId;
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

		KydkhpDBPK that = (KydkhpDBPK) o;

		if (hpId != that.hpId) return false;
		if (year != that.year) return false;
		if (semesterId != null ? !semesterId.equals(that.semesterId) : that.semesterId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = hpId;
		result = 31 * result + (semesterId != null ? semesterId.hashCode() : 0);
		result = 31 * result + year;
		return result;
	}
}
