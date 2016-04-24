package edu.westga.attendance;

import org.junit.Test;

import edu.westga.attendance.model.Course;

import static org.junit.Assert.assertEquals;

/**
 * Created by Wayne on 4/23/2016.
 *
 * Test class for course object
 */
public class CourseWhenCreateSetGetCourse {
    @Test
    public void ShouldSuccessfullyCreateCourse() throws Exception {
        Course newCourse = new Course("CS101");
        assertEquals("CS101", newCourse.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowExceptionForNullName() throws Exception {
        Course newCourse = new Course(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowExceptionForBlankName() throws Exception {
        Course newCourse = new Course("");
    }

    @Test
    public void ShouldSuccessfullyGetCourseName() throws Exception {
        Course newCourse = new Course("CS101");
        assertEquals("CS101", newCourse.getCourseName());
    }

    @Test
    public void ShouldSuccessfullyGetCourseID() throws Exception {
        Course newCourse = new Course(123, "CS101");
        assertEquals(123, newCourse.getCourseID());
    }
}
