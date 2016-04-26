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
 * Tests the data returned by the report queries that will be displayed in the fragments
 */
public class ReportQueryTests extends ActivityInstrumentationTestCase2<MainActivity> {

    public ReportQueryTests() {
        super(MainActivity.class);
    }

    public void testGetAttendanceForStudentDatePresent() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student wayne = new Student(1, "Wayne", "Davidson");
        String aDate = "2016-04-20";

        ArrayList<Attendance> result = db.getAttendanceForStudentDate(wayne, aDate);

        assertEquals(1, result.get(0).getPresent());

        activity.finish();
    }

    public void testGetAttendanceForStudentDateAbsent() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student wayne = new Student(1, "Wayne", "Davidson");
        String aDate = "2016-04-21";

        ArrayList<Attendance> result = db.getAttendanceForStudentDate(wayne, aDate);

        assertEquals(0, result.get(0).getPresent());

        activity.finish();
    }

    public void testGetAttendanceForStudentDateSize() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student wayne = new Student(1, "Wayne", "Davidson");
        String aDate = "2016-04-20";

        ArrayList<Attendance> result = db.getAttendanceForStudentDate(wayne, aDate);

        assertEquals(2, result.size());

        activity.finish();
    }

    public void testGetAttendanceForCourseDateSize() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Course cs101 = new Course(1, "CS101");
        String aDate = "2016-04-20";

        ArrayList<Attendance> result = db.getAttendanceForCourseDate(cs101, aDate);

        assertEquals(5, result.size());

        activity.finish();
    }

    public void testGetAttendanceForCourseDatePresent() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Course cs101 = new Course(1, "CS101");
        String aDate = "2016-04-20";

        ArrayList<Attendance> result = db.getAttendanceForCourseDate(cs101, aDate);

        assertEquals(1, result.get(0).getPresent());

        activity.finish();
    }

    public void testGetAttendanceForCourseDateAbsent() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Course cs101 = new Course(1, "CS101");
        String aDate = "2016-04-20";

        ArrayList<Attendance> result = db.getAttendanceForCourseDate(cs101, aDate);

        assertEquals(0, result.get(3).getPresent());

        activity.finish();
    }

    public void testGetAttendanceForCourseDateRangeSize() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Course cs101 = new Course(1, "CS101");
        String aDate = "2016-04-15";
        String bDate = "2016-04-30";

        ArrayList<Attendance> result = db.getAttendanceForCourseDateRange(cs101, aDate, bDate);

        assertEquals(5, result.size());

        activity.finish();
    }

    public void testGetAttendanceForCourseDateRangePresentDays() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Course cs101 = new Course(1, "CS101");
        String aDate = "2016-04-15";
        String bDate = "2016-04-30";

        ArrayList<Attendance> result = db.getAttendanceForCourseDateRange(cs101, aDate, bDate);

        assertEquals(1, result.get(0).getCountPresent());

        activity.finish();
    }

    public void testGetAttendanceForCourseDateRangeTotalDays() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Course cs101 = new Course(1, "CS101");
        String aDate = "2016-04-15";
        String bDate = "2016-04-30";

        ArrayList<Attendance> result = db.getAttendanceForCourseDateRange(cs101, aDate, bDate);

        assertEquals(3, result.get(3).getCountDays());

        activity.finish();
    }

    public void testGetAttendanceForStudentDateRangeSize() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student wayne = new Student(1, "Wayne", "Davidson");
        String aDate = "2016-04-15";
        String bDate = "2016-04-30";

        ArrayList<Attendance> result = db.getAttendanceForStudentDateRange(wayne, aDate, bDate);

        assertEquals(2, result.size());

        activity.finish();
    }

    public void testGetAttendanceForStudentDateRangePresentDays() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student wayne = new Student(1, "Wayne", "Davidson");
        String aDate = "2016-04-15";
        String bDate = "2016-04-30";

        ArrayList<Attendance> result = db.getAttendanceForStudentDateRange(wayne, aDate, bDate);

        assertEquals(1, result.get(0).getCountPresent());

        activity.finish();
    }

    public void testGetAttendanceForStudentDateRangeTotalDays() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student wayne = new Student(1, "Wayne", "Davidson");
        String aDate = "2016-04-15";
        String bDate = "2016-04-30";

        ArrayList<Attendance> result = db.getAttendanceForStudentDateRange(wayne, aDate, bDate);

        assertEquals(3, result.get(0).getCountDays());

        activity.finish();
    }
}
