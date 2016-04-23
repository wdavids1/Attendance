package edu.westga.attendance.model;

/**
 * Created by Wayne on 4/9/2016.
 *
 * This represents a course with a name
 */
public class Course {

    private int courseID;
    private String courseName;

    public Course() {

    }

    public Course(int courseID, String courseName) {
        this.courseID = courseID;
        this.courseName = courseName;
    }

    public Course(String courseName) {
        this.courseName = courseName;
    }

    public void setCourseID(int courseID) { this.courseID = courseID; }

    public int getCourseID() { return this.courseID; }

    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getCourseName() { return this.courseName; }
}
