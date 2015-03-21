package com.attendance.student.itu.studentattendance;

/**
 * Created by nafiurrashid on 2/25/15.
 */
public class AvailableClass {
    String course_name ;
    String professor_name ;

    public AvailableClass(String course_name) {
        super();

        this.course_name = course_name;
        this.professor_name = professor_name;

    }


    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getProfessor_name() {
        return professor_name;
    }

    public void setProfessor_name(String professor_name) {
        this.professor_name = professor_name;
    }
}
