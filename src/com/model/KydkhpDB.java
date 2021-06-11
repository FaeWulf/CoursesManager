package com.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class KydkhpDB implements Serializable {
	private int id;
	private String semesterId;
	private int year;
	private Timestamp start;
	private Timestamp end;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		KydkhpDB kydkhpDB = (KydkhpDB) o;

		if (id != kydkhpDB.id) return false;
		if (year != kydkhpDB.year) return false;
		if (semesterId != null ? !semesterId.equals(kydkhpDB.semesterId) : kydkhpDB.semesterId != null) return false;
		if (start != null ? !start.equals(kydkhpDB.start) : kydkhpDB.start != null) return false;
		if (end != null ? !end.equals(kydkhpDB.end) : kydkhpDB.end != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (semesterId != null ? semesterId.hashCode() : 0);
		result = 31 * result + year;
		result = 31 * result + (start != null ? start.hashCode() : 0);
		result = 31 * result + (end != null ? end.hashCode() : 0);
		return result;
	}
}
