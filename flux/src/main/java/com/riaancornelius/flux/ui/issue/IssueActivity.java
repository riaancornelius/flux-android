package com.riaancornelius.flux.ui.issue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.BitmapRequest;
import com.riaancornelius.flux.BaseActivity;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.api.ImageSpiceService;
import com.riaancornelius.flux.jira.api.request.issue.IssueRequest;
import com.riaancornelius.flux.jira.api.request.issue.UpdateIssueRequest;
import com.riaancornelius.flux.jira.domain.author.Author;
import com.riaancornelius.flux.jira.domain.issue.Attachment;
import com.riaancornelius.flux.jira.domain.issue.Comments;
import com.riaancornelius.flux.jira.domain.issue.Issue;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * User: riaan.cornelius
 */
public class IssueActivity extends BaseActivity {

    private static final int USER_SELECT = 1;
    private static final String TAG = "IssueActivity";
    private String issueKey;
    private TextView keyField;
    private TextView summaryField;
    private LinearLayout assignedToFields;
    private TextView assignedToField;
    private ImageView assignedToImage;
    private ProgressBar imageProgress;

    private Issue issue;

    private SpiceManager imageSpiceManager = new SpiceManager(ImageSpiceService.class);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        Intent intent = getIntent();
        issueKey = intent.getStringExtra(INTENT_KEY_ISSUE_ID);
        if (issueKey == null) {
            finish();
        }
        initUIComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        performRequest(issueKey);
        imageSpiceManager.start(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        imageSpiceManager.shouldStop();
    }

    private String lastRequestCacheKey;

    private void initUIComponents() {
        keyField = (TextView) findViewById(R.id.issue_key);
        summaryField = (TextView) findViewById(R.id.issue_summary);
        assignedToFields = (LinearLayout) findViewById(R.id.user_fields);
        assignedToField = (TextView) findViewById(R.id.issue_assigned_to);
        assignedToImage = (ImageView) findViewById(R.id.issue_assigned_image);
        imageProgress = (ProgressBar) findViewById(R.id.loader);

        assignedToImage.setVisibility(View.INVISIBLE);
        imageProgress.setVisibility(View.VISIBLE);

        assignedToFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IssueActivity.this, UserSelectActivity.class);
                startActivityForResult(intent, USER_SELECT);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == USER_SELECT && resultCode != RESULT_CANCELED) {
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
                spiceManager.getFromCacheAndLoadFromNetworkIfExpired(request, "update",
                        DurationInMillis.ONE_WEEK, new IssueUpdateListener());
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
                DurationInMillis.ONE_WEEK,
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
                    lastRequestCacheKey, DurationInMillis.ONE_WEEK,
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
                Log.d(TAG, "Issue returned is null");
                return;
            }

            issue = issueReturned;

            keyField.setText(issueReturned.getKey());
            summaryField.setText(issueReturned.getFields().getSummary());

            if (issueReturned.getFields().getAssignee() != null) {
                String displayName = issueReturned.getFields().getAssignee().getDisplayName();
                assignedToField.setText(displayName);
                BitmapRequest imageRequest = new BitmapRequest(issueReturned.getFields().getAssignee().getAvatarUrls().getFortyEightSquareUrl(), new File(getFilesDir(), displayName+".cache"));
                imageSpiceManager.getFromCacheAndLoadFromNetworkIfExpired(imageRequest, "image"+displayName, DurationInMillis.ONE_WEEK, new ImageListener());
            } else {
                assignedToField.setText(R.string.unassigned);
                BitmapRequest imageRequest = new BitmapRequest("https://secure.gravatar.com/avatar/17f13d9f230593332dba4190eb839037?d=mm&s=48", new File(getFilesDir(),"unassigned.cache"));
                imageSpiceManager.getFromCacheAndLoadFromNetworkIfExpired(imageRequest, "imageunassigned", DurationInMillis.ALWAYS_RETURNED, new ImageListener());
            }

            setDescription(issueReturned.getFields().getDescription());

            if (!(issueReturned.getFields().getCommentList() == null) &&
                    !issueReturned.getFields().getCommentList().getComments().isEmpty()) {
                setComments(issueReturned.getFields().getCommentList());
            } else {
                setComments(null);
            }

            if (issueReturned.getFields().getAttachmentList() != null &&
                    !issueReturned.getFields().getAttachmentList().isEmpty()) {
                setAttachments(issueReturned.getFields().getAttachmentList());
            } else {
                setAttachments(null);
            }

            IssueActivity.this.afterRequest();
        }
    }

    private void setDescription(String description) {
        TextView descriptionText = (TextView) findViewById(R.id.issue_description);
        View noDescription = findViewById(R.id.issue_description_empty_text);
        if (description != null && !description.isEmpty()) {
            descriptionText.setText(description);
            descriptionText.setVisibility(View.VISIBLE);
            noDescription.setVisibility(View.GONE);
        } else {
            descriptionText.setVisibility(View.GONE);
            noDescription.setVisibility(View.VISIBLE);
        }
    }

    private void setComments(Comments commentList) {
        LinearLayout comments = (LinearLayout) findViewById(R.id.issue_comments_list);
        View noComments = findViewById(R.id.issue_comments_empty_text);
        if (commentList != null) {
            comments.setVisibility(View.VISIBLE);
            noComments.setVisibility(View.GONE);
            CommentAdapter commentsAdapter = new CommentAdapter(getLayoutInflater(), commentList);
            final int adapterCount = commentsAdapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = commentsAdapter.getView(i, null, null);
                comments.addView(item);
            }
        } else {
            comments.setVisibility(View.GONE);
            noComments.setVisibility(View.VISIBLE);
        }
    }

    private void setAttachments(ArrayList<Attachment> attachments) {
        LinearLayout attList = (LinearLayout) findViewById(R.id.issue_attachment_list);
        View noAttachments = findViewById(R.id.issue_attachments_empty_text);
        if (attachments != null) {
            attList.setVisibility(View.VISIBLE);
            noAttachments.setVisibility(View.GONE);
            AttachmentsAdapter adapter = new AttachmentsAdapter(getLayoutInflater(), attachments);
            final int adapterCount = adapter.getCount();
            for (int i = 0; i < adapterCount; i++) {
                View item = adapter.getView(i, null, null);
                attList.addView(item);
            }
        } else {
            attList.setVisibility(View.GONE);
            noAttachments.setVisibility(View.VISIBLE);
        }
    }

    private class IssueUpdateListener implements RequestListener<String> {

        @Override
        public void onRequestFailure(SpiceException e) {
            Log.d(TAG, "Request failed",e);
            Toast.makeText(IssueActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            IssueActivity.this.afterRequest();
        }

        @Override
        public void onRequestSuccess(String s) {
            Log.d(TAG, "Success: " + s);
            Toast.makeText(IssueActivity.this, "Success!", Toast.LENGTH_LONG).show();
            performRequest(issueKey);
        }
    }

    private class ImageListener implements RequestListener<Bitmap> {
        @Override
        public void onRequestFailure(SpiceException e) {
            Log.e(TAG, "Could not load image", e);
            imageProgress.setVisibility(View.INVISIBLE);
            Toast.makeText(IssueActivity.this, "Could not load user image", Toast.LENGTH_LONG);

            IssueActivity.this.afterRequest();
        }

        @Override
        public void onRequestSuccess(Bitmap bitmap) {
            assignedToImage.setVisibility(View.VISIBLE);
            assignedToImage.setImageBitmap(bitmap);
            imageProgress.setVisibility(View.INVISIBLE);

            IssueActivity.this.afterRequest();
        }
    }

}
