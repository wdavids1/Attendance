package edu.westga.attendance.model;

import edu.westga.attendance.model.Course;
import edu.westga.attendance.model.Student;

/**
 * Created by Wayne on 4/9/2016.
 *
 * Represents the students in a course
 */
public class StudentInCourse {

    private int id;
    private Student student;
    private Course course;

    public StudentInCourse() {

    }

    public StudentInCourse(int id, Student student, Course course) {
        this.id = id;
        this.student = student;
        this.course = course;
    }

    public StudentInCourse(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getId() { return this.id; }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Student getStudent() { return this.student; }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() { return this.course; }
}
