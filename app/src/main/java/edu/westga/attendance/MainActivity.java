package edu.westga.attendance;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StudentEditFragment.EditStudentListener, StudentDisplayFragment.StudentDisplayListener, CourseEditFragment.EditCourseListener, StudentInCourseEditFragment.EditStudentInCourseListener,
takeAttendanceFragment.TakeAttendanceListener {

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

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //   public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStudentDisplay() {

    }

    public void onAddStudentButtonClick(View view) {
        try {
            FragmentManager fm = getFragmentManager();
            StudentEditFragment studentEditFragment = new StudentEditFragment();
            studentEditFragment.show(fm, "Add Student");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onAddCourseButtonClick(View view) {
        try {
            FragmentManager fm = getFragmentManager();
            CourseEditFragment courseEditFragment = new CourseEditFragment();
            courseEditFragment.show(fm, "Add Course");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onAddStudentToCourseButtonClick(View view) {
        try {
            FragmentManager fm = getFragmentManager();
            StudentInCourseEditFragment courseEditFragment = new StudentInCourseEditFragment();
            courseEditFragment.show(fm, "Add Course");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onTakeAttendanceButtonClick(View view) {
        try {
            Course course = (Course)this.course.getSelectedItem();

            DBHandler db = new DBHandler(this, null, null, 1);

            if (db.checkIfAttendanceExists(course) == 0 && db.checkIfCourseHasStudents(course) != 0) {
                FragmentManager fm = getFragmentManager();
                takeAttendanceFragment attendanceFragment = new takeAttendanceFragment();
                attendanceFragment.setCourseID(course.getCourseID());
                attendanceFragment.show(fm, "Take Attendance");
            } else {
                Toast.makeText(getApplicationContext(), "Attendance has already been taken or course has no students", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "An error has occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onRebuildDBButtonClick(View view) {
        try {
            DBHandler db = new DBHandler(this, null, null, 1);
            db.rebuildDB();
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
        CourseSpinnerAdapter  courseAdapter;
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        List<Course> courses = dbHandler.getAllCourses();
        courseAdapter = new CourseSpinnerAdapter(this,
                android.R.layout.simple_spinner_item , courses );
        course.setAdapter(courseAdapter);

        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
