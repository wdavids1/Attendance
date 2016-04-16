package edu.westga.attendance;

/**
 * Created by Wayne on 4/9/2016.
 *
 * This represents a student with a first and last name
 */
public class Student {

    private int studentID;
    private String firstName;
    private String lastName;

    public Student() {

    }

    public Student(int studentID, String firstName, String lastName) {
        this.studentID = studentID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Student(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public void setStudentID(int studentID) { this.studentID = studentID; }

    public int getStudentID() { return this.studentID; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getFirstName() { return this.firstName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getLastName() { return this.lastName; }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
