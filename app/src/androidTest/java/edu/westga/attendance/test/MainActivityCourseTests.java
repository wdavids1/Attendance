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
 * Tests the course related functions for the main activity
 */
public class MainActivityCourseTests extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityCourseTests() {
        super(MainActivity.class);
    }

    public void testCheckCourseExists() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        assertEquals(1, db.checkIfCourseExists("CS101"));

        activity.finish();
    }

    public void testCheckCourseExistsDifferentCase() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        assertEquals(1, db.checkIfCourseExists("cs101"));

        activity.finish();
    }

    public void testCheckCourseNotExist() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        assertEquals(0, db.checkIfCourseExists("CS1001"));

        activity.finish();
    }

    public void testAddCourse() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        activity.onCourseEdit("CS303");

        assertEquals(1, db.checkIfCourseExists("CS303"));

        activity.finish();
    }

    public void testCourseNotAddedWithMissingName() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        activity.onCourseEdit("");

        assertEquals(0, db.checkIfCourseExists(""));

        activity.finish();
    }

    public void testGetAllCourses() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        assertEquals(3, db.getAllCourses().size());

        activity.finish();
    }

    public void testGetAllStudentsInCourse() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        assertEquals(2, db.getAllStudentsInCourse(2).size());

        activity.finish();
    }
}
