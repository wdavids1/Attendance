package edu.westga.attendance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Wayne on 4/9/2016.
 */
public class StudentWhenCreateStudent {

    @Test
    public void ShouldSuccessfullyCreateStudent() throws Exception {
        Student newStudent = new Student("Wayne", "Davidson");
        assertEquals("Wayne Davidson", newStudent.toString());
    }

    @Test
    public void ShouldSuccessfullyCreateStudentAndSetNames() throws Exception {
        Student newStudent = new Student();
        newStudent.setFirstName("Wayne");
        newStudent.setLastName("Davidson");
        assertEquals("Wayne Davidson", newStudent.toString());
    }
}
