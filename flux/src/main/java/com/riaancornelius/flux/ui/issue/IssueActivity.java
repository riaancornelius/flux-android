package com.riaancornelius.flux.ui.issue;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.riaancornelius.flux.BaseActivity;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.api.request.issue.IssueRequest;
import com.riaancornelius.flux.jira.domain.issue.Comment;
import com.riaancornelius.flux.jira.domain.issue.Issue;
import com.riaancornelius.flux.jira.domain.sprint.Sprint;
import roboguice.inject.InjectView;

/**
 * User: riaan.cornelius
 */
public class IssueActivity extends BaseActivity {

    private String issueKey;
    private TextView keyField;
    private TextView summaryField;
    private TextView assignedToField;
    private TextView descriptionField;
    @InjectView(R.id.issue_comments_list) private ListView commentsList;

    private ArrayAdapter<String> commentsAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        Intent intent = getIntent();
        issueKey = intent.getStringExtra("issueKey");
        if (issueKey == null) {
            finish();
        }
        initUIComponents();
    }

    private String lastRequestCacheKey;

    private void initUIComponents() {
        keyField = (TextView) findViewById(R.id.issue_key);
        summaryField = (TextView) findViewById(R.id.issue_summary);
        assignedToField = (TextView) findViewById(R.id.issue_assigned_to);
        descriptionField = (TextView) findViewById(R.id.issue_description);

        commentsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);
        commentsList.setAdapter(commentsAdapter);

        performRequest(issueKey);
    }

    private void performRequest(String IssueKey) {
        beforeRequest();

        IssueRequest request = new IssueRequest(IssueKey);
        lastRequestCacheKey = request.createCacheKey();
        spiceManager.execute(request, lastRequestCacheKey,
                DurationInMillis.ONE_MINUTE, new IssueRequestListener());
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
        public void onRequestSuccess(Issue issue) {
            // Issue could be null just if contentManager.getFromCache(...)
            // doesn't return anything.
            if (issue == null) {
                return;
            }

            keyField.setText(issue.getKey());
            summaryField.setText(issue.getFields().getSummary());
            assignedToField.setText( //getResources().getString(R.string.assigned_to) + " " +
                    issue.getFields().getAssignee().getDisplayName());
            descriptionField.setText(issue.getFields().getDescription());

            // Just hide comments list if no comments
            if (issue.getFields().getCommentList() == null ||
                    issue.getFields().getCommentList().getComments().isEmpty()) {
                commentsList.setVisibility(View.GONE);
                return;
            }

            commentsAdapter.clear();

            for (Comment comment : issue.getFields().getCommentList().getComments()) {
                commentsAdapter.add(comment.getBody());
            }

            commentsAdapter.notifyDataSetChanged();

            IssueActivity.this.afterRequest();
        }
    }
}
