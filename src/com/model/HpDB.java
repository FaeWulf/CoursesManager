package com.model;

import java.io.Serializable;

public class HpDB implements Serializable {
	private int id;
	private int subjectId;
	private String className;
	private int weekDay;
	private int time;
	private int slot;
	private int teacherId;

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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getWeekDay() {
		return weekDay;
	}

	public void setWeekDay(int weekDay) {
		this.weekDay = weekDay;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getSlot() {
		return slot;
	}

	public void setSlot(int slot) {
		this.slot = slot;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		HpDB hpDB = (HpDB) o;

		if (id != hpDB.id) return false;
		if (subjectId != hpDB.subjectId) return false;
		if (weekDay != hpDB.weekDay) return false;
		if (time != hpDB.time) return false;
		if (slot != hpDB.slot) return false;
		if (teacherId != hpDB.teacherId) return false;
		if (className != null ? !className.equals(hpDB.className) : hpDB.className != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + subjectId;
		result = 31 * result + (className != null ? className.hashCode() : 0);
		result = 31 * result + weekDay;
		result = 31 * result + time;
		result = 31 * result + slot;
		result = 31 * result + teacherId;
		return result;
	}
}
