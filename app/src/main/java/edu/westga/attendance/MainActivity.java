package edu.westga.attendance;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.westga.attendance.model.Attendance;
import edu.westga.attendance.model.Course;
import edu.westga.attendance.model.Student;
import edu.westga.attendance.model.StudentInCourse;

public class MainActivity extends AppCompatActivity implements StudentEdit_Fragment.EditStudentListener, CourseEdit_Fragment.EditCourseListener, StudentInCourseEdit_Fragment.EditStudentInCourseListener,
Attendance_Fragment.TakeAttendanceListener, Report_CourseDateAttendance_Selection_Fragment.ViewResultListener, Report_CourseDateRangeAttendance_Selection_Fragment.ViewResultListener, Report_StudentDateAttendance_Selection_Fragment.ViewResultListener,
        Report_StudentDateRangeAttendance_Selection_Fragment.ViewResultListener, ConfirmDialog_Fragment.ConfirmListener, Report_StudentCourseDateRangeAttendance_Selection_Fragment.ViewResultListener {

    private Spinner course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.course = (Spinner) findViewById(R.id.coursespinner);
        try {
            fillCourseSpinner();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //    return true;
        //}

        if (id == R.id.action_addStudent) {
            try {
                FragmentManager fm = getFragmentManager();
                StudentEdit_Fragment studentEditFragment = new StudentEdit_Fragment();
                studentEditFragment.show(fm, "Add Student");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (id == R.id.action_addCourse) {
            try {
                FragmentManager fm = getFragmentManager();
                CourseEdit_Fragment courseEditFragment = new CourseEdit_Fragment();
                courseEditFragment.show(fm, "Add Course");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (id == R.id.action_addStudentToCourse) {
            try {
                FragmentManager fm = getFragmentManager();
                StudentInCourseEdit_Fragment courseEditFragment = new StudentInCourseEdit_Fragment();
                courseEditFragment.show(fm, "Add Course");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (id == R.id.action_clearDB) {
            try {
                FragmentManager fm = getFragmentManager();
                ConfirmDialog_Fragment confirm = new ConfirmDialog_Fragment();
                confirm.setMessage("Are you sure you wish to delete all data, this cannot be undone?");
                confirm.setOption("Clear");
                confirm.show(fm, "Confirm DB Clear");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (id == R.id.action_rebuildDB) {
            try {
                FragmentManager fm = getFragmentManager();
                ConfirmDialog_Fragment confirm = new ConfirmDialog_Fragment();
                confirm.setMessage("Are you sure you wish to delete all data and load test data, this cannot be undone?");
                confirm.setOption("Test");
                confirm.show(fm, "Confirm DB Clear and Test Data");
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onTakeAttendanceButtonClick(View view) {
        try {
            Course course = (Course)this.course.getSelectedItem();

            DBHandler db = new DBHandler(this, null, null, 1);

            if (db.checkIfAttendanceExists(course) == 0 && db.checkIfCourseHasStudents(course) != 0) {
                FragmentManager fm = getFragmentManager();
                Attendance_Fragment attendanceFragment = new Attendance_Fragment();
                attendanceFragment.setCourseID(course.getCourseID());
                attendanceFragment.show(fm, "Take Attendance");
            } else {
                Toast.makeText(getApplicationContext(), "Attendance has already been taken or course has no students", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onGetAttendanceButtonClick(View view) {
        try {
            FragmentManager fm = getFragmentManager();
            Report_CourseDateAttendance_Selection_Fragment attendanceFragment = new Report_CourseDateAttendance_Selection_Fragment();
            attendanceFragment.show(fm, "Get report");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onGetAttendanceRangeButtonClick(View view) {
        try {
            FragmentManager fm = getFragmentManager();
            Report_CourseDateRangeAttendance_Selection_Fragment attendanceFragment = new Report_CourseDateRangeAttendance_Selection_Fragment();
            attendanceFragment.show(fm, "Get report");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onGetStudentAttendanceButtonClick(View view) {
        try {
            FragmentManager fm = getFragmentManager();
            Report_StudentDateAttendance_Selection_Fragment attendanceFragment = new Report_StudentDateAttendance_Selection_Fragment();
            attendanceFragment.show(fm, "Get report");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onGetStudentAttendanceRangeButtonClick(View view) {
        try {
            FragmentManager fm = getFragmentManager();
            Report_StudentDateRangeAttendance_Selection_Fragment attendanceFragment = new Report_StudentDateRangeAttendance_Selection_Fragment();
            attendanceFragment.show(fm, "Get report");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onGetStudentCourseAttendanceDetailButtonClick(View view) {
        try {
            FragmentManager fm = getFragmentManager();
            Report_StudentCourseDateRangeAttendance_Selection_Fragment attendanceFragment = new Report_StudentCourseDateRangeAttendance_Selection_Fragment();
            attendanceFragment.show(fm, "Get report");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStudentEdit(String firstName, String lastName) {
        try {
            Student newStudent = new Student(firstName, lastName);

            DBHandler dbHandler = new DBHandler(this, null, null, 1);

            if (dbHandler.checkIfStudentExists(firstName, lastName) == 0) {
                dbHandler.addStudent(newStudent);

                Toast.makeText(getApplicationContext(), "Student Added", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Student already exists", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCourseEdit(String courseName) {
        try {
            Course newCourse = new Course(courseName);

            DBHandler dbHandler = new DBHandler(this, null, null, 1);

            if (dbHandler.checkIfCourseExists(courseName) == 0) {
                dbHandler.addCourse(newCourse);

                Toast.makeText(getApplicationContext(), "Course Added", Toast.LENGTH_LONG).show();
                course.setAdapter(null);
                fillCourseSpinner();
            } else {
                Toast.makeText(getApplicationContext(), "Course already exists", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStudentInCourseEdit(Student student, Course course) {
        try {
            StudentInCourse newStudentInCourse = new StudentInCourse(student, course);

            DBHandler dbHandler = new DBHandler(this, null, null, 1);

            if (dbHandler.checkIfStudentInCourseExists(student, course) == 0) {
                dbHandler.addStudentInCourse(newStudentInCourse);

                Toast.makeText(getApplicationContext(), "Student Added to Course", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Student is already in that course", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onTakeAttendance(ArrayList<Attendance> attendance) {
        try {
            boolean error = false;
            DBHandler dbHandler = new DBHandler(this, null, null, 1);
            for (int i=0; i<attendance.size(); i++) {
                if (dbHandler.checkIfAttendanceExists(attendance.get(i)) == 0) {
                    dbHandler.addAttendance(attendance.get(i));
                } else {
                    error = true;
                }
            }

            if (error) {
                Toast.makeText(getApplicationContext(), "Attendance has already been taken", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), "Attendance Saved", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void fillCourseSpinner() {
        CourseSpinnerAdapter courseAdapter;
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        List<Course> courses = dbHandler.getAllCourses();
        courseAdapter = new CourseSpinnerAdapter(this,
                android.R.layout.simple_spinner_item , courses );
        course.setAdapter(courseAdapter);

        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onViewCourseDateReport(Course course, String date) {
        try {
            FragmentManager fm = getFragmentManager();
            Report_CourseDateAttendance_Fragment attendanceFragment = new Report_CourseDateAttendance_Fragment();
            attendanceFragment.setCourse(course);
            attendanceFragment.setDate(date);
            attendanceFragment.show(fm, "Get report");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewCourseDateRangeReport(Course course, String startdate, String enddate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());

        Date sdate = null;
        try {
            sdate = dateFormat.parse(startdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date edate = null;
        try {
            edate = dateFormat.parse(enddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            if (sdate.after(edate)) {
                Toast.makeText(getApplicationContext(), "Start date is after End date. ", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        try {
            FragmentManager fm = getFragmentManager();
            Report_CourseDateRangeAttendance_Fragment attendanceFragment = new Report_CourseDateRangeAttendance_Fragment();
            attendanceFragment.setCourse(course);
            attendanceFragment.setStartDate(startdate);
            attendanceFragment.setEndDate(enddate);
            attendanceFragment.show(fm, "Get report");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewStudentDateReport(Student student, String date) {
        try {
            FragmentManager fm = getFragmentManager();
            Report_StudentDateAttendance_Fragment attendanceFragment = new Report_StudentDateAttendance_Fragment();
            attendanceFragment.setStudent(student);
            attendanceFragment.setDate(date);
            attendanceFragment.show(fm, "Get report");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onViewStudentDateRangeReport(Student student, String startdate, String enddate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());

        Date sdate = null;
        try {
            sdate = dateFormat.parse(startdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date edate = null;
        try {
            edate = dateFormat.parse(enddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            if (sdate.after(edate)) {
                Toast.makeText(getApplicationContext(), "Start date is after End date. ", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        try {
            FragmentManager fm = getFragmentManager();
            Report_StudentDateRangeAttendance_Fragment attendanceFragment = new Report_StudentDateRangeAttendance_Fragment();
            attendanceFragment.setStudent(student);
            attendanceFragment.setStartDate(startdate);
            attendanceFragment.setEndDate(enddate);
            attendanceFragment.show(fm, "Get report");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConfirm(String option) {
        if (option.equals("Clear")) {
            DBHandler db = new DBHandler(this, null, null, 1);
            db.clearAllData();
            this.fillCourseSpinner();
        } else if (option.equals("Test")) {
            DBHandler db = new DBHandler(this, null, null, 1);
            db.rebuildDB();
            this.fillCourseSpinner();
        }
    }

    @Override
    public void onViewStudentDateRangeReport(Student student, Course course, String startdate, String enddate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());

        Date sdate = null;
        try {
            sdate = dateFormat.parse(startdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date edate = null;
        try {
            edate = dateFormat.parse(enddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            if (sdate.after(edate)) {
                Toast.makeText(getApplicationContext(), "Start date is after End date. ", Toast.LENGTH_LONG).show();
                return;
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        try {
            FragmentManager fm = getFragmentManager();
            Report_StudentCourseDateRangeAttendance_Fragment attendanceFragment = new Report_StudentCourseDateRangeAttendance_Fragment();
            attendanceFragment.setStudent(student);
            attendanceFragment.setCourse(course);
            attendanceFragment.setStartDate(startdate);
            attendanceFragment.setEndDate(enddate);
            attendanceFragment.show(fm, "Get report");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
