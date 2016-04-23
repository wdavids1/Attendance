package edu.westga.attendance;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.westga.attendance.R;

/**
 * Created by Wayne on 4/9/2016.
 *
 * Fragment for allowing a course to be created
 */
public class CourseEditFragment extends DialogFragment {
    private EditText courseName;
    private EditCourseListener listener;

    interface EditCourseListener{
        void onCourseEdit(String courseName);
    }

    public CourseEditFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.course_fragment, container, false);
        this.courseName = (EditText) theView.findViewById(R.id.courseName);

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

    private void closeButtonClicked(View v) {
        this.dismiss();
    }

    private void addButtonClicked(View v) {
        String CourseName = this.courseName.getText().toString();

        listener.onCourseEdit(CourseName);

        this.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (EditCourseListener)context;
    }

    public void setCourseName(String courseName) { this.courseName.setText(courseName); }
}
