package edu.westga.attendance;

/**
 * Created by Wayne on 4/9/2016.
 *
 * Represents attendance
 */
public class Attendance {
    private int id;
    private StudentInCourse studentInCourse;
    private String date;
    private int present;

    public Attendance() {

    }

    public Attendance(int id, StudentInCourse studentInCourse, String date, int present) {
        this.id = id;
        this.studentInCourse = studentInCourse;
        this.date = date;
        this.present = present;
    }

    public Attendance(StudentInCourse studentInCourse, String date, int present) {
        this.studentInCourse = studentInCourse;
        this.date = date;
        this.present = present;
    }

    public void setId(int id) { this.id = id; }

    public int getId() { return this.id; }

    public void setStudentInCourse(StudentInCourse studentInCourse) {
        this.studentInCourse = studentInCourse;
    }

    public StudentInCourse getStudentInCourse(){ return this.studentInCourse; }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() { return this.date; }

    public void setPresent(int present) { this.present = present; }

    public int getPresent() { return this.present; }

    @Override
    public String toString() {
        return this.studentInCourse.getStudent().getFirstName()
                + " " + this.studentInCourse.getStudent().getLastName()
                + " " + this.date + " " + this.present;
    }
}
