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
        setCourseID(courseID);
        setCourseName(courseName);
    }

    public Course(String courseName) {
        setCourseName(courseName);
    }

    public void setCourseID(int courseID) { this.courseID = courseID; }

    public int getCourseID() { return this.courseID; }

    public void setCourseName(String courseName) {
        if (courseName == null || courseName.isEmpty()) {
            throw new IllegalArgumentException("Coursename must not be null");
        }
        this.courseName = courseName; }

    public String getCourseName() { return this.courseName; }

    @Override
    public String toString() {
        return this.courseName;
    }
}
