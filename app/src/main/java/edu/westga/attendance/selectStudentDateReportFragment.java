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

import edu.westga.attendance.model.Student;

/**
 * Created by Wayne on 4/16/2016.
 *
 * View to pick a student and date to get a report for
 */
public class selectStudentDateReportFragment extends DialogFragment {
    private Spinner student;
    private ViewResultListener listener;
    private DatePicker datePicker;

    interface ViewResultListener {
        void onViewStudentDateReport(Student student, String date);
    }

    public selectStudentDateReportFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View theView = inflater.inflate(R.layout.studentdatereportselection_fragment, container, false);

        this.student = (Spinner) theView.findViewById(R.id.studentspinner);

        this.fillStudentSpinner();

        datePicker = (DatePicker) theView.findViewById(R.id.datePicker);

        final Button button = (Button) theView.findViewById(R.id.viewReportButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewReportButtonClicked(v);
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ViewResultListener)context;
    }

    private void viewReportButtonClicked(View v) {
        Student student = (Student)this.student.getSelectedItem();

        String day = checkDigit(this.datePicker.getDayOfMonth());
        String month = checkDigit(this.datePicker.getMonth() + 1);
        String year = checkDigit(this.datePicker.getYear());

        String date = year + "-" + month + "-" + day;

        listener.onViewStudentDateReport(student, date);

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

    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }
}
