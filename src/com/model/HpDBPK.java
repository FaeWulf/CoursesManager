package com.model;

import java.io.Serializable;

public class HpDBPK implements Serializable {
	private int id;
	private int subjectId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(int subjectId) {
		this.subjectId = subjectId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		HpDBPK hpDBPK = (HpDBPK) o;

		if (id != hpDBPK.id) return false;
		if (subjectId != hpDBPK.subjectId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + subjectId;
		return result;
	}
}
