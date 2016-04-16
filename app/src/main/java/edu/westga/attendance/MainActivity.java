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

public class MainActivity extends AppCompatActivity implements StudentEditFragment.EditStudentListener, StudentDisplayFragment.StudentDisplayListener, CourseEditFragment.EditCourseListener, StudentInCourseEditFragment.EditStudentInCourseListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
    public void onStudentEdit(String firstName, String lastName) {
        Student newStudent = new Student(firstName, lastName);
        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        dbHandler.addStudent(newStudent);
    }

    @Override
    public void onStudentDisplay() {

    }

    public void onAddStudentButtonClick(View view) {
        FragmentManager fm = getFragmentManager();
        StudentEditFragment studentEditFragment = new StudentEditFragment();
        studentEditFragment.show(fm, "Add Student");
    }

    public void onAddCourseButtonClick(View view) {
        FragmentManager fm = getFragmentManager();
        CourseEditFragment courseEditFragment = new CourseEditFragment();
        courseEditFragment.show(fm, "Add Course");
    }

    public void onAddStudentToCourseButtonClick(View view) {
        FragmentManager fm = getFragmentManager();
        StudentInCourseEditFragment courseEditFragment = new StudentInCourseEditFragment();
        courseEditFragment.show(fm, "Add Course");
    }

    public void onTakeAttendanceButtonClick(View view) {
        FragmentManager fm = getFragmentManager();
        takeAttendanceFragment attendanceFragment = new takeAttendanceFragment();
        attendanceFragment.setCourseID(1);
        attendanceFragment.show(fm, "Take Attendance");
    }

    public void onRebuildDBButtonClick(View view) {
        DBHandler db = new DBHandler(this, null, null, 1);
        db.rebuildDB();
    }

    @Override
    public void onCourseEdit(String courseName) {
        Course newCourse = new Course(courseName);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        dbHandler.addCourse(newCourse);
    }

    @Override
    public void onStudentInCourseEdit(Student student, Course course) {
        StudentInCourse newStudentInCourse = new StudentInCourse(student, course);

        DBHandler dbHandler = new DBHandler(this, null, null, 1);
        dbHandler.addStudentInCourse(newStudentInCourse);

        StudentDisplayFragment studentDisplayFragment = (StudentDisplayFragment)
                getSupportFragmentManager().findFragmentById(R.id.studentDisplayFragment);

        studentDisplayFragment.setFirstName(student.getFirstName());
        studentDisplayFragment.setLastName(course.getCourseName());
    }
}
