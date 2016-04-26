package edu.westga.attendance.test;

import android.test.ActivityInstrumentationTestCase2;
import edu.westga.attendance.MainActivity;

/**
 * Created by Wayne on 4/24/2016.
 *
 * Tests for primary main activity
 */
public class MainActivityTests extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTests() {
        super(MainActivity.class);
    }

    public void testActivityExists() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
        activity.finish();
    }


}
