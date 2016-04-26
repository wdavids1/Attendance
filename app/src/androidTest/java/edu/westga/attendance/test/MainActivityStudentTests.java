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
public class MainActivityStudentTests extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityStudentTests() {
        super(MainActivity.class);
    }

    public void testCheckStudentExists() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();
        assertEquals(1, db.checkIfStudentExists("Wayne", "Davidson"));

        activity.finish();
    }

    public void testCheckStudentExistsInDifferentCase() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();
        assertEquals(1, db.checkIfStudentExists("wayne", "davidson"));

        activity.finish();
    }

    public void testCheckStudentNotExist() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();
        assertEquals(0, db.checkIfStudentExists("Barney", "Rubble"));

        activity.finish();
    }

    public void testAddStudent() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        activity.onStudentEdit("Andrew", "Jackson");

        assertEquals(1, db.checkIfStudentExists("Andrew", "Jackson"));

        activity.finish();
    }

    public void testStudentNotAddedWithMissingName() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        activity.onStudentEdit("", "Jackson");

        assertEquals(0, db.checkIfStudentExists("", "Jackson"));

        activity.finish();
    }

    public void testGetAllStudents() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        assertEquals(5, db.getAllStudents().size());

        activity.finish();
    }
}
