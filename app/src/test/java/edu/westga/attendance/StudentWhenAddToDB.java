package edu.westga.attendance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Wayne on 4/9/2016.
 */
public class StudentWhenAddToDB {
    @Test
    public void ShouldSuccessfullyAddStudent() throws Exception {
        Student newStudent = new Student("Wayne", "Davidson");

        DBHandler myDB = new DBHandler(null, null, null, 1);

        myDB.addStudent(newStudent);

        assertEquals("Wayne Davidson", newStudent.toString());
    }
}
