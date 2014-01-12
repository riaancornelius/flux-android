package com.riaancornelius.flux;

import android.test.ActivityInstrumentationTestCase2;
import com.riaancornelius.flux.ui.MainActivity;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void testActivity() {
        MainActivity activity = getActivity();
        assertNotNull(activity);
    }
}

