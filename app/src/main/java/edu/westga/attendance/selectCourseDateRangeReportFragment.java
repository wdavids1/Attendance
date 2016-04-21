package edu.westga.attendance;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.util.List;

/**
 * Created by Wayne on 4/16/2016.
 *
 * View to pick a course and date to get a report for
 */
public class selectCourseDateRangeReportFragment extends DialogFragment {
    private Spinner course;
    private ViewResultListener listener;
    private DatePicker startDate;
    private DatePicker endDate;

    interface ViewResultListener {
        void onViewCourseDateRangeReport(Course course, String startdate, String enddate);
    }

    public selectCourseDateRangeReportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.coursedaterangereportselection_fragment, container, false);

        this.course = (Spinner) theView.findViewById(R.id.coursespinner);

        this.fillCourseSpinner();

        startDate = (DatePicker) theView.findViewById(R.id.startDate);
        endDate = (DatePicker) theView.findViewById(R.id.endDate);

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

        String day = checkDigit(this.startDate.getDayOfMonth());
        String month = checkDigit(this.startDate.getMonth() + 1);
        String year = checkDigit(this.startDate.getYear());

        String startDate = year + "-" + month + "-" + day;

        String endday = checkDigit(this.endDate.getDayOfMonth());
        String endmonth = checkDigit(this.endDate.getMonth() + 1);
        String endyear = checkDigit(this.endDate.getYear());

        String endDate = endyear + "-" + endmonth + "-" + endday;

        listener.onViewCourseDateRangeReport(course, startDate, endDate);

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
