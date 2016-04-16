package edu.westga.attendance;

import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Wayne on 4/15/2016.
 *
 * Allows one to take attendance
 */
public class takeAttendanceFragment extends DialogFragment {
    private LinearLayout mLinearListView;
    private int courseid;
    private ArrayList<Attendance> attendance = new ArrayList<Attendance>();
    private Date dNow = new Date();

    interface TakeAttendanceListener {
        void onTakeAttendance();
    }

    public takeAttendanceFragment() {

    }

    public void setCourseID(int courseid) {
        this.courseid = courseid;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.takeattendance_fragment, container, false);
        mLinearListView = (LinearLayout) theView.findViewById(R.id.linear_listview);

        DBHandler dbHandler = new DBHandler(this.getActivity(), null, null, 1);
        List<StudentInCourse> students = dbHandler.getAllStudentsInCourse(courseid);

        TextView courseName = (TextView) theView.findViewById(R.id.textViewCourse);
        courseName.setText("Course");

        for (int i=0; i<students.size(); i++) {
            Attendance newAttendance = new Attendance(students.get(i), dNow.toString(), 1);
            attendance.add(newAttendance);

            LayoutInflater inflater1 = null;
            inflater1 = (LayoutInflater) this.getActivity().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mLinearView = inflater1.inflate(R.layout.student_list_detail, null);

            final TextView firstName = (TextView) mLinearView.findViewById(R.id.textViewName);
            final TextView textid = (TextView) mLinearView.findViewById(R.id.textViewID);
            final ToggleButton toggle = (ToggleButton) mLinearView.findViewById(R.id.toggleButton);
            toggle.setBackgroundColor(Color.GREEN);

            final String name = "  " + students.get(i).getStudent().getFirstName() + " " + students.get(i).getStudent().getLastName();
            //final int id = students.get(i).getId();

            firstName.setText(name);
            //textid.setText(Integer.toString(id));
            textid.setText(Integer.toString(i));

            mLinearListView.addView(mLinearView);

            toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        toggle.setBackgroundColor(Color.RED);
                        textid.setVisibility(View.VISIBLE);
                        int ID = Integer.parseInt(textid.getText().toString());
                        Attendance temp = new Attendance();
                        temp.setStudentInCourse(attendance.get(ID).getStudentInCourse());
                        temp.setDate(attendance.get(ID).getDate());
                        temp.setPresent(0);
                        attendance.set(ID, temp);
                        //firstName.setText(attendance.get(ID).toString());
                    } else {
                        toggle.setBackgroundColor(Color.GREEN);
                        textid.setVisibility(View.VISIBLE);
                        int ID = Integer.parseInt(textid.getText().toString());
                        Attendance temp = new Attendance();
                        temp.setStudentInCourse(attendance.get(ID).getStudentInCourse());
                        temp.setDate(attendance.get(ID).getDate());
                        temp.setPresent(1);
                        attendance.set(ID, temp);
                        //firstName.setText(attendance.toString());
                    }
                }
            });

        }
        return theView;
    }

}
