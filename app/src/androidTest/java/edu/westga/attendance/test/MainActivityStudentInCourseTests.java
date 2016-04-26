package edu.westga.attendance.test;

import android.test.ActivityInstrumentationTestCase2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import edu.westga.attendance.DBHandler;
import edu.westga.attendance.MainActivity;
import edu.westga.attendance.model.Attendance;
import edu.westga.attendance.model.Course;
import edu.westga.attendance.model.Student;
import edu.westga.attendance.model.StudentInCourse;

/**
 * Created by Wayne on 4/24/2016.
 *
 * Tests the studentincourse related functions for the main activity
 */
public class MainActivityStudentInCourseTests extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityStudentInCourseTests() {
        super(MainActivity.class);
    }

    public void testCheckStudentInCourseExists() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student wayne = new Student(1, "Wayne", "Davidson");

        Course cs101 = new Course(1, "CS101");

        assertEquals(1, db.checkIfStudentInCourseExists(wayne, cs101));

        activity.finish();
    }

    public void testCheckStudentInCourseNotExist() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student wayne = new Student(1, "Wayne", "Davidson");

        Course cs103 = new Course(3, "CS103");

        assertEquals(0, db.checkIfStudentInCourseExists(wayne, cs103));

        activity.finish();
    }

    public void testAddStudentToCourse() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student newStudent = new Student(6, "Wilma", "Flintstone");
        Course newCourse = new Course(4, "CS201");

        activity.onStudentEdit(newStudent.getFirstName(), newStudent.getLastName());
        activity.onCourseEdit(newCourse.getCourseName());

        activity.onStudentInCourseEdit(newStudent, newCourse);

        assertEquals(1, db.checkIfStudentInCourseExists(newStudent, newCourse));

        activity.finish();
    }

    public void testCheckIfCourseHasStudentsTrue() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Course cs101 = new Course(1, "CS101");

        assertEquals(5, db.checkIfCourseHasStudents(cs101));

        activity.finish();
    }

    public void testCheckIfCourseHasStudentsFalse() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Course cs103 = new Course(3, "CS103");

        assertEquals(0, db.checkIfCourseHasStudents(cs103));

        activity.finish();
    }
}
