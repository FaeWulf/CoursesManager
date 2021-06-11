package com.faewulf.application;

import com.model.*;
import Database.*;

import java.util.ArrayList;
import java.util.List;
public class allData {
    public static List<subjectDB> subjectList = subject.getSubjectList();
    public static List<accountDB> accountList = account.getAccountList();
    public static List<semesterDB> semesterList = semester.getSemesterList();
    public static List<clazzDB> clazzList = clazz.getClazzList();
    public static List<StudentDB> studentList = student.getstudentList();
    public static List<StudyatDB> studyAtList = studyAt.getStudyAtList();
    public static List<KydkhpDB> kydkhpList = kydkhp.getKydkhpList();
    public static List<HpDB> hpList = hp.getHpList();
    public static List<scheduleTime> scheduleTimeList = new scheduleTime().output();

    public static class scheduleTime{
        public int index;
        public String time;

        scheduleTime(int index, String time) {
            this.time = time;
            this.index = index;
        }

        scheduleTime(){
        }

        public List<scheduleTime> output(){
            List<scheduleTime> result = new ArrayList<>();
            result.add(new scheduleTime(1, "7:30 - 9:30"));
            result.add(new scheduleTime(2, "9:30 - 11:30"));
            result.add(new scheduleTime(3, "13:30 - 15:30"));
            result.add(new scheduleTime(4, "15:30 - 17:30"));
            return result;
        }

        @Override
        public String toString() {
            return "#" + index + " (" + time + ")";
        }
    }
}
