package com.training.android.selficheck.Datas;

public class SubjectsData extends Subj_StudentsData {


    private String CourseCode, CourseName, CourseSchedule, CourseRoom, SubjectPassword;
    private long TeacherID;

    public SubjectsData() {
    }


    public SubjectsData(String CourseCode, String CourseName, String CourseSchedule, String CourseRoom, String SubjectPassword, long TeacherID) {
        this.CourseCode = CourseCode;
        this.CourseName = CourseName;
        this.CourseSchedule = CourseSchedule;
        this.CourseRoom = CourseRoom;
        this.SubjectPassword = SubjectPassword;
        this.TeacherID = TeacherID;
    }

    @Override
    public String getCourseCode() {
        return CourseCode;
    }

    @Override
    public void setCourseCode(String courseCode) {
        this.CourseCode = courseCode;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String courseName) {
        this.CourseName = courseName;
    }

    public String getCourseSchedule() {
        return CourseSchedule;
    }

    public void setCourseSchedule(String courseSchedule) {
        this.CourseSchedule = courseSchedule;
    }

    public String getCourseRoom() {
        return CourseRoom;
    }

    public void setCourseRoom(String courseRoom) {
        this.CourseRoom = courseRoom;
    }

    public String getSubjectPassword() {
        return SubjectPassword;
    }

    public void setSubjectPassword(String subjectPassword) {
        this.SubjectPassword = subjectPassword;
    }

    public long getTeacherID() {
        return TeacherID;
    }

    public void setTeacherID(long teacherID) {
        this.TeacherID = teacherID;
    }
}
