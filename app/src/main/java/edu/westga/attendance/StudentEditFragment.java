package edu.westga.attendance;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.content.Context;
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
 * Fragment to create a student
 */
public class StudentEditFragment extends DialogFragment {
    private EditText firstName;
    private EditText lastName;
    private EditStudentListener listener;

    interface EditStudentListener{
        void onStudentEdit(String firstName, String lastName);
    }

    public StudentEditFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.student_fragment, container, false);
        this.firstName = (EditText) theView.findViewById(R.id.firstName);
        this.lastName = (EditText) theView.findViewById(R.id.lastName);

        final Button addButton = (Button) theView.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addButtonClicked(v);
            }
        });
        return theView;
    }

    private void addButtonClicked(View v) {
        String FirstName = this.firstName.getText().toString();
        String LastName = this.lastName.getText().toString();

        listener.onStudentEdit(FirstName, LastName);

        this.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (EditStudentListener)context;
    }

    public void setFirstName(String firstName) { this.firstName.setText(firstName); }

    public void setLastName(String lastName) { this.lastName.setText(lastName);}
}