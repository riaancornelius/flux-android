package com.riaancornelius.flux.ui.issue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.riaancornelius.flux.BaseActivity;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.api.request.issue.IssueRequest;
import com.riaancornelius.flux.jira.api.request.issue.UpdateIssueRequest;
import com.riaancornelius.flux.jira.domain.author.Author;
import com.riaancornelius.flux.jira.domain.issue.Issue;
import com.riaancornelius.flux.ui.components.CustomPagerAdapter;

/**
 * User: riaan.cornelius
 */
public class IssueActivity extends BaseActivity {

    private static final String COMMENTS_KEY = "comments";
    private static final String ATTACHMENTS_KEY = "attachments";
    private static final int USER_SELECT = 1;
    private static final String TAG = "IssueActivity";
    private String issueKey;
    private TextView keyField;
    private TextView summaryField;
    private TextView assignedToField;
    private TextView descriptionField;

    private CustomPagerAdapter pagerAdapter;
    private Issue issue;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        Intent intent = getIntent();
        issueKey = intent.getStringExtra("issueKey");
        if (issueKey == null) {
            finish();
        }
        pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        initUIComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        performRequest(issueKey);
    }

    private String lastRequestCacheKey;

    private void initUIComponents() {
        keyField = (TextView) findViewById(R.id.issue_key);
        summaryField = (TextView) findViewById(R.id.issue_summary);
        assignedToField = (TextView) findViewById(R.id.issue_assigned_to);
        descriptionField = (TextView) findViewById(R.id.issue_description);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(pagerAdapter);

        assignedToField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IssueActivity.this, UserSelectActivity.class);
                startActivityForResult(intent, USER_SELECT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == USER_SELECT) {
            //handle it here
            String userKey = intent.getStringExtra(UserSelectActivity.USER_KEY);
            Log.d(TAG, "Reassigning issue " + issueKey + " to user " + userKey);
            //TODO update issue with new user (see UpdateIssueRequest)
            if (issue != null) {
                if (issue.getFields().getAssignee() == null) {
                    issue.getFields().setAssignee(new Author());
                }
                issue.getFields().getAssignee().setKey(userKey);
                UpdateIssueRequest request = new UpdateIssueRequest(issue);
                spiceManager.getFromCacheAndLoadFromNetworkIfExpired(request, "update", DurationInMillis.ALWAYS_EXPIRED, new IssueUpdateListener());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, intent);
        }
    }

    private void performRequest(String IssueKey) {
        beforeRequest();
        IssueRequest request = new IssueRequest(IssueKey);
        lastRequestCacheKey = request.createCacheKey();
        spiceManager.execute(request, lastRequestCacheKey,
                DurationInMillis.NEVER, // ONLY FOR TESTING
                //DurationInMillis.ONE_MINUTE,
                new IssueRequestListener());
    }

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
            spiceManager.addListenerIfPending(Issue.class,
                    lastRequestCacheKey, new IssueRequestListener());
            spiceManager.getFromCache(Issue.class,
                    lastRequestCacheKey, DurationInMillis.ONE_MINUTE,
                    new IssueRequestListener());
        }
    }

    private class IssueRequestListener implements
            RequestListener<Issue> {
        @Override
        public void onRequestFailure(SpiceException e) {
            Toast.makeText(IssueActivity.this,
                    "Error during request: " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
            IssueActivity.this.setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess(Issue issueReturned) {
            // Issue could be null just if contentManager.getFromCache(...)
            // doesn't return anything.
            if (issueReturned == null) {
                return;
            }

            issue = issueReturned;

            keyField.setText(issueReturned.getKey());
            summaryField.setText(issueReturned.getFields().getSummary());
            if (issueReturned.getFields().getAssignee() != null) {
                assignedToField.setText( //getResources().getString(R.string.assigned_to) + " " +
                        issueReturned.getFields().getAssignee().getDisplayName());
            } else {
                assignedToField.setText(R.string.unassigned);
            }
            descriptionField.setText(issueReturned.getFields().getDescription());

            if (!(issueReturned.getFields().getCommentList() == null) &&
                    !issueReturned.getFields().getCommentList().getComments().isEmpty()) {
                IssueCommentsFragment commentsFragment = new IssueCommentsFragment();
                commentsFragment.setComments(issueReturned.getFields().getCommentList());
                pagerAdapter.addFragment(COMMENTS_KEY, commentsFragment);
            }

            if (issueReturned.getFields().getAttachmentList() != null && !issueReturned.getFields().getAttachmentList().isEmpty()) {
                IssueAttachmentsFragment attachmentsFragment = new IssueAttachmentsFragment();
                attachmentsFragment.setAttachments(issueReturned.getFields().getAttachmentList());
                pagerAdapter.addFragment(ATTACHMENTS_KEY, attachmentsFragment);
            }


            IssueActivity.this.afterRequest();
        }
    }

    private class IssueUpdateListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException e) {
            Log.d(TAG, "Request failed",e);
            Toast.makeText(IssueActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess(String s) {
            Log.d(TAG, "Success: " + s);
            Toast.makeText(IssueActivity.this, s, Toast.LENGTH_LONG).show();
        }
    }

}
