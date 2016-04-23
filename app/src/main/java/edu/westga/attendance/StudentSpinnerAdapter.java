package edu.westga.attendance;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import edu.westga.attendance.model.Student;


/**
 * Created by Wayne on 4/11/2016.
 *
 * Creates a spinner of students
 */
public class StudentSpinnerAdapter extends ArrayAdapter<Student> {

    private Context mContext;
    private List<Student> mValues;

    public StudentSpinnerAdapter(Context context,
                                 int textViewResourceId, List<Student> objects) {
        super(context, textViewResourceId, objects);
        this.mContext = context;
        this.mValues = objects;
    }

    @Override
    public int getCount(){
        return mValues.size();
    }

    @Override
    public Student getItem(int position){
        return mValues.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //This is for the first item before dropdown or default state.
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setTextSize(18);
        label.setText("Student: " + mValues.get(position).getFirstName() + " " + mValues.get(position).getLastName());
        label.setHeight(120);
        label.setGravity(Gravity.CENTER );
        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        //This is when user click the spinner and list of item display
        // beneath it
        TextView label = new TextView(mContext);
        label.setTextColor(Color.BLACK);
        label.setBackgroundColor(Color.parseColor("#CCFFFF"));
        label.setTextSize(18);
        label.setText(mValues.get(position).getFirstName() + " " + mValues.get(position).getLastName());
        label.setHeight(70);
        label.setGravity(Gravity.CENTER );

        return label;
    }
}
