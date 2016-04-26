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
 * Tests for primary main activity
 */
public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTests() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
        activity.finish();
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

    public void testCheckAttendanceExistsForCurrentDate() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student wayne = new Student(1, "Wayne", "Davidson");
        Course cs101 = new Course(1, "CS101");
        StudentInCourse waynecs101 = new StudentInCourse(1, wayne, cs101);
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String aDate = sdf.format(date);
        Attendance newAttendance = new Attendance(waynecs101, aDate, 1);
        ArrayList<Attendance> attendanceList = new ArrayList<>();
        attendanceList.add(newAttendance);
        activity.onTakeAttendance(attendanceList);

        assertEquals(1, db.checkIfAttendanceExists(cs101));

        activity.finish();
    }

    public void testCheckAttendanceNotExistsForCurrentDate() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student wayne = new Student(1, "Wayne", "Davidson");
        Course cs101 = new Course(1, "CS101");

        assertEquals(0, db.checkIfAttendanceExists(cs101));

        activity.finish();
    }

    public void testTakeAttendance() {
        MainActivity activity = getActivity();
        DBHandler db = new DBHandler(activity.getApplicationContext(), null, null, 1);
        db.rebuildDB();

        Student newStudent = new Student(6, "Wilma", "Flintstone");
        Course newCourse = new Course(4, "CS201");

        activity.onStudentEdit(newStudent.getFirstName(), newStudent.getLastName());
        activity.onCourseEdit(newCourse.getCourseName());

        activity.onStudentInCourseEdit(newStudent, newCourse);

        StudentInCourse newStudentInCourse = new StudentInCourse(8, newStudent, newCourse);

        String aDate = "2016-04-25";

        Attendance newAttendance = new Attendance(newStudentInCourse, aDate, 1);

        ArrayList<Attendance> attendanceList = new ArrayList<>();
        attendanceList.add(newAttendance);

        activity.onTakeAttendance(attendanceList);

        assertEquals(1, db.checkIfAttendanceExists(newAttendance));

        activity.finish();
    }
}
