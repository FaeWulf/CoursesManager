package com.model;

import java.io.Serializable;

public class DkhpDB implements Serializable {
	private int studentId;
	private int hpId;

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getHpId() {
		return hpId;
	}

	public void setHpId(int hpId) {
		this.hpId = hpId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DkhpDB dkhpDB = (DkhpDB) o;

		if (studentId != dkhpDB.studentId) return false;
		if (hpId != dkhpDB.hpId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = studentId;
		result = 31 * result + hpId;
		return result;
	}
}
