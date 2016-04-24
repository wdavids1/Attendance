package edu.westga.attendance;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import java.util.List;

import edu.westga.attendance.model.Course;
import edu.westga.attendance.model.Student;


/**
 * Created by Wayne on 4/9/2016.
 *
 * Fragment to put a student in a course
 */
public class StudentInCourseEditFragment extends DialogFragment {
    private Spinner student;
    private Spinner course;
    private EditStudentInCourseListener listener;

    interface EditStudentInCourseListener{
        void onStudentInCourseEdit(Student student, Course course);
    }

    public StudentInCourseEditFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.studentincourse_fragment, container, false);
        this.student = (Spinner) theView.findViewById(R.id.studentspinner);
        this.course = (Spinner) theView.findViewById(R.id.coursespinner);

        this.fillStudentSpinner();
        this.fillCourseSpinner();

        final Button addButton = (Button) theView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addButtonClicked(v);
            }
        });

        final Button closeButton = (Button) theView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                closeButtonClicked(v);
            }
        });

        return theView;
    }

    private void addButtonClicked(View v) {
        Student student = (Student)this.student.getSelectedItem();
        Course course = (Course)this.course.getSelectedItem();

        listener.onStudentInCourseEdit(student, course);

        this.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (EditStudentInCourseListener)context;
    }

    private void closeButtonClicked(View v) {
        this.dismiss();
    }

    private void fillStudentSpinner() {
        StudentSpinnerAdapter studentAdapter;
        DBHandler dbHandler = new DBHandler(this.getActivity(), null, null, 1);
        List<Student> students = dbHandler.getAllStudents();
        studentAdapter = new StudentSpinnerAdapter(this.getActivity(),
                android.R.layout.simple_spinner_item , students );
        student.setAdapter(studentAdapter);

        studentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private void fillCourseSpinner() {
        CourseSpinnerAdapter courseAdapter;
        DBHandler dbHandler = new DBHandler(this.getActivity(), null, null, 1);
        List<Course> courses = dbHandler.getAllCourses();
        courseAdapter = new CourseSpinnerAdapter(this.getActivity(),
                android.R.layout.simple_spinner_item , courses );
        course.setAdapter(courseAdapter);

        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

}
