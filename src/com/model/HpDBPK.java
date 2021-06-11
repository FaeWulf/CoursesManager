package com.model;

import java.io.Serializable;

public class HpDBPK implements Serializable {
	private int id;
	private int subjectId;
	private int kydkhpId;

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

	public int getKydkhpId() {
		return kydkhpId;
	}

	public void setKydkhpId(int kydkhpId) {
		this.kydkhpId = kydkhpId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		HpDBPK hpDBPK = (HpDBPK) o;

		if (id != hpDBPK.id) return false;
		if (subjectId != hpDBPK.subjectId) return false;
		if (kydkhpId != hpDBPK.kydkhpId) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + subjectId;
		result = 31 * result + kydkhpId;
		return result;
	}
}
