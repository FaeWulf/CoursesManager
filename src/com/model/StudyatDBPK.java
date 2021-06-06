package com.model;

import java.io.Serializable;

public class StudyatDBPK implements Serializable {
	private String classId;
	private int studentId;

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StudyatDBPK that = (StudyatDBPK) o;

		if (studentId != that.studentId) return false;
		if (classId != null ? !classId.equals(that.classId) : that.classId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = classId != null ? classId.hashCode() : 0;
		result = 31 * result + studentId;
		return result;
	}
}
