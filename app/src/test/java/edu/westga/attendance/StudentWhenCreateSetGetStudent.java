package edu.westga.attendance;

import org.junit.Test;

import edu.westga.attendance.model.Student;

import static org.junit.Assert.assertEquals;

/**
 * Created by Wayne on 4/9/2016.
 *
 * Test class for student
 */
public class StudentWhenCreateSetGetStudent {

    @Test
    public void ShouldSuccessfullyCreateStudent() throws Exception {
        Student newStudent = new Student("Wayne", "Davidson");
        assertEquals("Wayne Davidson", newStudent.toString());
    }

    @Test
    public void ShouldSuccessfullyCreateStudentWithID() throws Exception {
        Student newStudent = new Student(123, "Wayne", "Davidson");
        assertEquals(123, newStudent.getStudentID());
    }

    @Test
    public void ShouldSuccessfullyCreateStudentAndSetNames() throws Exception {
        Student newStudent = new Student();
        newStudent.setFirstName("Wayne");
        newStudent.setLastName("Davidson");
        assertEquals("Wayne Davidson", newStudent.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowException() throws Exception {
        Student newStudent = new Student(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowExceptionForBlankFName() throws Exception {
        Student newStudent = new Student("", "Davidson");
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowExceptionForBlankLName() throws Exception {
        Student newStudent = new Student("Wayne", "");
    }

    @Test
    public void ShouldSuccessfullyGetFirstName() throws Exception {
        Student newStudent = new Student();
        newStudent.setFirstName("Wayne");
        newStudent.setLastName("Davidson");
        assertEquals("Wayne", newStudent.getFirstName());
    }

    @Test
    public void ShouldSuccessfullyGetLastName() throws Exception {
        Student newStudent = new Student();
        newStudent.setFirstName("Wayne");
        newStudent.setLastName("Davidson");
        assertEquals("Davidson", newStudent.getLastName());
    }

    @Test
    public void ShouldSuccessfullyGetID() throws Exception {
        Student newStudent = new Student();
        newStudent.setStudentID(123);
        newStudent.setFirstName("Wayne");
        newStudent.setLastName("Davidson");
        assertEquals(123, newStudent.getStudentID());
    }
}
