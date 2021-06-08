package com.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "studyat", schema = "coursesmanager", catalog = "")
@IdClass(StudyatDBPK.class)
public class StudyatDB implements Serializable {
	private String classId;
	private int studentId;

	@Id
	@Column(name = "class_id", nullable = false, length = 255)
	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@Id
	@Column(name = "student_id", nullable = false)
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

		StudyatDB studyatDB = (StudyatDB) o;

		if (studentId != studyatDB.studentId) return false;
		if (classId != null ? !classId.equals(studyatDB.classId) : studyatDB.classId != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = classId != null ? classId.hashCode() : 0;
		result = 31 * result + studentId;
		return result;
	}

}
