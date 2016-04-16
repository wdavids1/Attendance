package edu.westga.attendance;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Wayne on 4/9/2016.
 *
 * Displays a student
 */
public class StudentDisplayFragment extends Fragment {
    private EditText firstName;
    private EditText lastName;

    private StudentDisplayListener listener;

    interface StudentDisplayListener{
        void onStudentDisplay();
    }

    public StudentDisplayFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.student_fragment, container, false);

        final Button addButton = (Button) theView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addButtonClicked(v);
            }
        });

        this.firstName = (EditText) theView.findViewById(R.id.firstName);
        this.lastName = (EditText) theView.findViewById(R.id.lastName);

        return theView;
    }

    private void addButtonClicked(View v) { listener.onStudentDisplay(); }

    public void setFirstName(String firstName) { this.firstName.setText(firstName); }

    public void setLastName(String lastName) { this.lastName.setText(lastName);}
}
