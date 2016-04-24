package edu.westga.attendance;

import org.junit.Test;

import edu.westga.attendance.model.Course;
import edu.westga.attendance.model.Student;
import edu.westga.attendance.model.StudentInCourse;

import static org.junit.Assert.assertEquals;
/**
 * Created by Wayne on 4/23/2016.
 *
 * Tests for StudentInCourse Objects
 */
public class StudentInCourseCreateSetGet {
    @Test
    public void ShouldSuccessfullyCreateStudentInCourse() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        assertEquals("Wayne Davidson, CS101", studentInCourse.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowExceptionForNullStudent() throws Exception {
        Course newCourse = new Course("CS101");
        StudentInCourse studentInCourse = new StudentInCourse(null, newCourse);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowExceptionForNullCourse() throws Exception {
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, null);
    }

    @Test
    public void ShouldSuccessfullySetStudentAndCourse() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse();
        studentInCourse.setStudent(newStudent);
        studentInCourse.setCourse(newCourse);
        assertEquals("Wayne Davidson, CS101", studentInCourse.toString());
    }

    @Test
    public void ShouldSuccessfullyGetStudent() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        assertEquals("Wayne Davidson", studentInCourse.getStudent().toString());
    }

    @Test
    public void ShouldSuccessfullyGetCourse() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        assertEquals("CS101", studentInCourse.getCourse().toString());
    }

    @Test
    public void ShouldSuccessfullyGetID() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(123, newStudent, newCourse);
        assertEquals(123, studentInCourse.getId());
    }
}
