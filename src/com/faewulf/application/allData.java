package com.faewulf.application;

import com.model.*;
import Database.*;
import java.util.List;
public class allData {
    public static List<subjectDB> subjectList = subject.getSubjectList();
    public static List<accountDB> accountList = account.getAccountList();
    public static List<semesterDB> semesterList = semester.getSemesterList();
    public static List<clazzDB> clazzList = clazz.getClazzList();
    public static List<StudentDB> studentList = student.getstudentList();
    public static List<StudyatDB> studyAtList = studyAt.getStudyAtList();
}
