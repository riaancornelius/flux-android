package com.riaancornelius.flux.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.riaancornelius.flux.BaseActivity;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.domain.Settings;
import com.riaancornelius.flux.jira.api.request.sprint.SprintReportRequest;
import com.riaancornelius.flux.jira.domain.sprint.Sprint;
import com.riaancornelius.flux.jira.domain.sprint.report.SprintReport;
import com.riaancornelius.flux.ui.components.EncryptedEditText;
import com.riaancornelius.flux.ui.issue.IssueActivity;
import com.riaancornelius.flux.ui.sprint.SprintsActivity;
import com.riaancornelius.flux.util.DateUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by riaan.cornelius on 2013/12/22.
 */
public class MainActivity extends BaseActivity {

    private String lastRequestCacheKey;
    private TextView sprintName;
    private TextView sprintDates;
    private Button sprintSummaryTotal;
    private Button sprintSummaryCompleted;
    private Button sprintSummaryUncompleted;

    private long boardId;
    private long currentSprint;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after
     * previously being shut down then this Bundle contains the data it most
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: Look these values up through the rest API
        boardId = 1L;
        currentSprint = 3L;

        // TODO: GET RID OF THIS - ONLY FOR TESTING!!:
        if(!isUserDetailsSet()){
            LOAD_TEST_SETTINGS();
        }

        if(!isUserDetailsSet()){
            super.loadSettingsActivity();
            finish();
        }

        sprintName = (TextView) findViewById(R.id.sprintName);
        sprintDates = (TextView) findViewById(R.id.sprintDates);

        sprintSummaryTotal = (Button) findViewById(R.id.totalIssues);
        sprintSummaryCompleted = (Button) findViewById(R.id.completedIssues);
        sprintSummaryUncompleted = (Button) findViewById(R.id.uncompletedIssues);

        performSprintReportRequest();
    }

    // Load settings from external file
    private void LOAD_TEST_SETTINGS() {
        // find the properties file
        File sdcard = Environment.getExternalStorageDirectory();
        File folder = new File(sdcard,"flux");
        File file = new File(folder,"testSettings.properties");

        try {
            Log.d("TEST", "Loading settings from: "+file.getAbsolutePath());
            Properties props = new Properties();
            // Read properties file
            props.load(new FileInputStream(file));
            // Save the properties to the shared preferences where the app expects them
            Log.d("TEST", "File Loaded: "+file.getAbsolutePath());
            SharedPreferences settings = getSharedPreferences(Settings.JIRA_SHARED_PREFERENCES_KEY, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(Settings.JIRA_USERNAME_KEY, props.getProperty("username"));
            editor.putString(Settings.JIRA_PASSWORD_KEY,
                    EncryptedEditText.rot13Encode(props.getProperty("password")).toString());
            editor.putString(Settings.JIRA_BASE_URL_KEY, props.getProperty("baseUrl"));
            editor.commit();
            Log.d("TEST", "Settings saved");
        } catch (IOException e) {
            Log.e("TEST", e.getMessage());
        }
    }

    private void performSprintReportRequest() {
        super.beforeRequest();
        SprintReportRequest request = new SprintReportRequest(boardId, currentSprint);
        lastRequestCacheKey = request.createCacheKey();
        spiceManager.execute(request, lastRequestCacheKey, DurationInMillis.ONE_MINUTE * 5, new SprintReportRequestListener());
    }

    public void onScanTicketClick(View view) {
        super.scanTicketCode();
    }

    public void onArViewClick(View view) {
        // TODO: ONLY FOR TESTING
        Intent myIntent = new Intent(this, IssueActivity.class);
        myIntent.putExtra("issueKey", "FLUX-44");
        this.startActivity(myIntent);
    }

    public void onViewSprintsClick(View view) {
        Intent myIntent = new Intent(MainActivity.this, SprintsActivity.class);
        myIntent.putExtra(BaseActivity.INTENT_KEY_BOARD_ID, boardId);
        MainActivity.this.startActivity(myIntent);
    }

    /*
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (!TextUtils.isEmpty(lastRequestCacheKey)) {
            outState.putString(KEY_LAST_REQUEST_CACHE_KEY, lastRequestCacheKey);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey(KEY_LAST_REQUEST_CACHE_KEY)) {
            lastRequestCacheKey = savedInstanceState
                    .getString(KEY_LAST_REQUEST_CACHE_KEY);
            spiceManager.addListenerIfPending(Sprints.class,
                    lastRequestCacheKey, new SprintsRequestListener());
            spiceManager.getFromCache(Sprints.class,
                    lastRequestCacheKey, DurationInMillis.ONE_MINUTE,
                    new SprintsRequestListener());
        }
    }
    */

    //inner class of your spiced Activity
    private class SprintReportRequestListener implements RequestListener<SprintReport> {

        @Override
        public void onRequestFailure(SpiceException e) {
            MainActivity.this.afterRequest();
            Toast.makeText(MainActivity.this,
                    "Error during request: " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
        }

        @Override
        public void onRequestSuccess(SprintReport sprintReport) {
            MainActivity.this.afterRequest();
            //update your UI
            Sprint activeSprint = sprintReport.getSprint();
            if (activeSprint!=null) {
                sprintName.setText(activeSprint.getName());
                sprintDates.setText(
                        DateUtil.formatDate(activeSprint.getStartDate())
                        + " - " +
                        DateUtil.formatDate(activeSprint.getEndDate()));
                sprintSummaryTotal.setText(Long.toString(sprintReport.getContents().getAllIssuesEstimateSum().getValue()));
                sprintSummaryCompleted.setText(Long.toString(sprintReport.getContents().getCompletedIssuesEstimateSum().getValue()));
                sprintSummaryUncompleted.setText(Long.toString(sprintReport.getContents().getIncompletedIssuesEstimateSum().getValue()));
            }
            Log.d("TEST", "Found sprints: " + sprintReport.getSprint().getName());
        }
    }
}
