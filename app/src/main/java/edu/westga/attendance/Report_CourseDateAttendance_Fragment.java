package edu.westga.attendance;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import edu.westga.attendance.model.Attendance;
import edu.westga.attendance.model.Course;

/**
 * Created by Wayne on 4/20/2016.
 *
 * An attendance report for the selected date and course
 */
public class Report_CourseDateAttendance_Fragment extends DialogFragment{

    private LinearLayout mLinearListView;
    private Course course;
    private String theDate;
    private ArrayList<Attendance> attendance = new ArrayList<>();

    public Report_CourseDateAttendance_Fragment() {

    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setDate(String theDate) {
        this.theDate = theDate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.report_course_date_attendance_fragment, container, false);
        mLinearListView = (LinearLayout) theView.findViewById(R.id.linear_listview);

        DBHandler dbHandler = new DBHandler(this.getActivity(), null, null, 1);
        attendance = dbHandler.getAttendanceForCourseDate(course, theDate);

        TextView courseName = (TextView) theView.findViewById(R.id.textViewCourse);
        TextView dates = (TextView) theView.findViewById(R.id.textViewDate);

        if (attendance.size() > 0) {
            courseName.setText("Course: " + attendance.get(0).getStudentInCourse().getCourse().getCourseName());
            dates.setText(formatDate(theDate));

            for (int i=0; i<attendance.size(); i++) {
                LayoutInflater inflater1 = (LayoutInflater) this.getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View mLinearView = inflater1.inflate(R.layout.report_student_list_item_detail, null);

                final TextView firstName = (TextView) mLinearView.findViewById(R.id.textViewName);
                final TextView present = (TextView) mLinearView.findViewById(R.id.textViewPresent);

                final String name = "  " + attendance.get(i).getStudentInCourse().getStudent().getFirstName()
                        + " " + attendance.get(i).getStudentInCourse().getStudent().getLastName();

                firstName.setText(name);

                if (attendance.get(i).getPresent() == 1) {
                    present.setText("Present");
                    present.setBackgroundColor(Color.GREEN);
                } else {
                    present.setText("Absent");
                    present.setBackgroundColor(Color.RED);
                }

                mLinearListView.addView(mLinearView);
            }
        } else {
            courseName.setText("No data found for " + formatDate(theDate));
        }
        final Button closeButton = (Button) theView.findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    closeButtonClicked(v);
                }
            });
        return theView;
    }

    private void closeButtonClicked(View v) {
        this.dismiss();
    }

    private String formatDate(String theDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());

        Date date = new Date();
        try {
            date = dateFormat.parse(theDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateFormat.format(date);
    }
}
