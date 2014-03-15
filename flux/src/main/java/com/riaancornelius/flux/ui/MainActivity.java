package com.riaancornelius.flux.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.riaancornelius.flux.BaseActivity;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.domain.Settings;
import com.riaancornelius.flux.jira.api.request.sprint.SprintsRequest;
import com.riaancornelius.flux.jira.domain.sprint.Sprint;
import com.riaancornelius.flux.jira.domain.sprint.Sprints;
import com.riaancornelius.flux.ui.components.CustomPagerAdapter;
import com.riaancornelius.flux.ui.issue.IssueActivity;
import com.riaancornelius.flux.ui.issue.IssueListActivity;
import com.riaancornelius.flux.ui.sprint.SprintFragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by riaan.cornelius on 2013/12/22.
 */
public class MainActivity extends BaseActivity {
    private long boardId;
    private long currentSprint;
    private CustomPagerAdapter sprintPagerAdapter;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *                           previously being shut down then this Bundle contains the data it most
     *                           recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sprintPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(sprintPagerAdapter);

        // TODO: Look these values up through the rest API
        boardId = 1L;
        currentSprint = 4L;

        // TODO: GET RID OF THIS - ONLY FOR TESTING!!:
        if (!isUserDetailsSet()) {
            LOAD_TEST_SETTINGS();
        }

        if (!isUserDetailsSet()) {
            super.loadSettingsActivity();
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadAllSprints();
    }

    // Load settings from external file
    private void LOAD_TEST_SETTINGS() {
        // find the properties file
        File sdcard = Environment.getExternalStorageDirectory();
        File folder = new File(sdcard, "flux");
        File file = new File(folder, "testSettings.properties");

        try {
            Log.d("TEST", "Loading settings from: " + file.getAbsolutePath());
            Properties props = new Properties();
            // Read properties file
            props.load(new FileInputStream(file));
            // Save the properties to the shared preferences where the app expects them
            Log.d("TEST", "File Loaded: " + file.getAbsolutePath());
            //SharedPreferences settings = getSharedPreferences(Settings.JIRA_SHARED_PREFERENCES_KEY, 0);
            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(Settings.JIRA_USERNAME_KEY, props.getProperty("username"));
            editor.putString(Settings.JIRA_PASSWORD_KEY, props.getProperty("password"));
            editor.putString(Settings.JIRA_BASE_URL_KEY, props.getProperty("baseUrl"));
            editor.commit();
            Log.d("TEST", "Settings saved");
        } catch (IOException e) {
            Log.e("TEST", "IOException", e);
        }
    }

    private void loadAllSprints() {
        super.beforeRequest();
        SprintsRequest request = new SprintsRequest(boardId, true);
        spiceManager.execute(request, request.createCacheKey(), DurationInMillis.ONE_DAY, new SprintRequestListener());
    }

    public void onScanTicketClick(View view) {
        super.scanTicketCode();
    }

    public void onArViewClick(View view) {
        // TODO: ONLY FOR TESTING
        Intent myIntent = new Intent(this, IssueActivity.class);
        myIntent.putExtra(INTENT_KEY_ISSUE_ID, "FLUX-44");
        this.startActivity(myIntent);
    }

    public void onViewSprintsClick(View view) {
        Intent myIntent = new Intent(MainActivity.this, IssueListActivity.class);
        myIntent.putExtra(BaseActivity.INTENT_KEY_BOARD_ID, boardId);
        myIntent.putExtra(BaseActivity.INTENT_KEY_SPRINT_ID, currentSprint);
        MainActivity.this.startActivity(myIntent);
    }

    public void onIssueListButtonClick(View view) {
        Intent myIntent = new Intent(MainActivity.this, IssueListActivity.class);
        myIntent.putExtra(BaseActivity.INTENT_KEY_BOARD_ID, boardId);
        myIntent.putExtra(BaseActivity.INTENT_KEY_SPRINT_ID, currentSprint);
        myIntent.putExtra(BaseActivity.INTENT_KEY_REQUESTING_ID, view.getId());
        MainActivity.this.startActivity(myIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (!TextUtils.isEmpty(lastRequestCacheKey)) {
            outState.putString(KEY_LAST_REQUEST_CACHE_KEY, lastRequestCacheKey);
        }
        super.onSaveInstanceState(outState);
    }

    private class SprintRequestListener implements RequestListener<Sprints> {
        @Override
        public void onRequestFailure(SpiceException e) {
            MainActivity.this.afterRequest();
            Toast.makeText(MainActivity.this, "Could not load sprints: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess(Sprints sprints) {
            MainActivity.this.afterRequest();
            for (Sprint s : sprints.getSprints()) {
                SprintFragment fragment = new SprintFragment(s, boardId, spiceManager);
                sprintPagerAdapter.addFragment(s.getName(), fragment);
                //default view is active sprint
                if (s.getState().equals("ACTIVE")) {
                    ((ViewPager) findViewById(R.id.pager)).setCurrentItem(sprints.getSprints().indexOf(s));
                }
            }
        }
    }
}
