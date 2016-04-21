package edu.westga.attendance;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Wayne on 4/16/2016.
 *
 * View to pick a course and date to get a report for
 */
public class selectCourseDateReportFragment extends DialogFragment {
    private Spinner course;
    private ViewResultListener listener;
    private DatePicker datePicker;

    interface ViewResultListener {
        void onViewCourseDateReport(Course course, String date);
    }

    public selectCourseDateReportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.coursedatereportselection_fragment, container, false);

        this.course = (Spinner) theView.findViewById(R.id.coursespinner);

        this.fillCourseSpinner();

        datePicker = (DatePicker) theView.findViewById(R.id.datePicker);

        final Button button = (Button) theView.findViewById(R.id.viewReportButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewReportButtonClicked(v);
            }
        });

        return theView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ViewResultListener)context;
    }

    private void viewReportButtonClicked(View v) {
        Course course = (Course)this.course.getSelectedItem();

        String day = checkDigit(this.datePicker.getDayOfMonth());
        String month = checkDigit(this.datePicker.getMonth() + 1);
        String year = checkDigit(this.datePicker.getYear());

        String date = year + "-" + month + "-" + day;

        listener.onViewCourseDateReport(course, date);

        this.dismiss();
    }

    private void fillCourseSpinner() {
        CourseSpinnerAdapter  courseAdapter;
        DBHandler dbHandler = new DBHandler(this.getActivity(), null, null, 1);
        List<Course> courses = dbHandler.getAllCourses();
        courseAdapter = new CourseSpinnerAdapter(this.getActivity(),
                android.R.layout.simple_spinner_item , courses );
        course.setAdapter(courseAdapter);

        courseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
}
