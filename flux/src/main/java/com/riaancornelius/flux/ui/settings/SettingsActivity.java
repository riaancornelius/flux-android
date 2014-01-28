package com.riaancornelius.flux.ui.settings;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import android.preference.PreferenceFragment;
import com.riaancornelius.flux.R;

/**
 * User: riaan.cornelius
 */
public class SettingsActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.preferences);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}