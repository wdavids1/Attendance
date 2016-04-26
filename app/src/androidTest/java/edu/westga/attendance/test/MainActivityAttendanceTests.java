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
 * Tests the attendance related functions for the main activity
 */
public class MainActivityAttendanceTests extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityAttendanceTests() {
        super(MainActivity.class);
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
