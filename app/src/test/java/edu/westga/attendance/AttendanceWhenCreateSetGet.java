package edu.westga.attendance;

import org.junit.Test;

import edu.westga.attendance.model.Attendance;
import edu.westga.attendance.model.Course;
import edu.westga.attendance.model.Student;
import edu.westga.attendance.model.StudentInCourse;

import static org.junit.Assert.assertEquals;
/**
 * Created by Wayne on 4/23/2016.
 *
 * test class for attendance objects
 */
public class AttendanceWhenCreateSetGet {
    @Test
    public void ShouldSuccessfullyCreateAttendance() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(studentInCourse, "2016-04-20", 1);
        assertEquals("Wayne Davidson 2016-04-20 1", newAttendance.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowExceptionForNullStudentInCourse() throws Exception {
        Attendance newAttendance = new Attendance(null, "2016-04-20", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowExceptionForEmptyDate() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(studentInCourse, "", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowExceptionForNegativePresent() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(studentInCourse, "2016-04-20", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ShouldThrowExceptionForPresent2() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(studentInCourse, "2016-04-20", 2);
    }

    @Test
    public void ShouldSuccessfullyGetStudentInCourse() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(studentInCourse, "2016-04-20", 1);
        assertEquals("Wayne Davidson, CS101", newAttendance.getStudentInCourse().toString());
    }

    @Test
    public void ShouldSuccessfullyGetDate() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(studentInCourse, "2016-04-20", 1);
        assertEquals("2016-04-20", newAttendance.getDate());
    }

    @Test
    public void ShouldSuccessfullyGetPresent() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(studentInCourse, "2016-04-20", 1);
        assertEquals(1, newAttendance.getPresent());
    }

    @Test
    public void ShouldSuccessfullyGetID() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(12, studentInCourse, "2016-04-20", 1);
        assertEquals(12, newAttendance.getId());
    }

    @Test
    public void ShouldSuccessfullySetGetDaysCount() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(studentInCourse, "2016-04-20", 1);
        newAttendance.setCountDays(4);
        assertEquals(4, newAttendance.getCountDays());
    }

    @Test
    public void ShouldSuccessfullySetGetDaysPresentCount() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(studentInCourse, "2016-04-20", 1);
        newAttendance.setCountPresent(3);
        assertEquals(3, newAttendance.getCountPresent());
    }

    @Test
    public void ShouldSuccessfullySetDate() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(studentInCourse, "2016-04-20", 1);
        newAttendance.setDate("2016-04-21");
        assertEquals("Wayne Davidson 2016-04-21 1", newAttendance.toString());
    }

    @Test
    public void ShouldSuccessfullySetPresent() throws Exception {
        Course newCourse = new Course("CS101");
        Student newStudent = new Student("Wayne", "Davidson");
        StudentInCourse studentInCourse = new StudentInCourse(newStudent, newCourse);
        Attendance newAttendance = new Attendance(studentInCourse, "2016-04-20", 1);
        newAttendance.setPresent(0);
        assertEquals("Wayne Davidson 2016-04-20 0", newAttendance.toString());
    }
}
