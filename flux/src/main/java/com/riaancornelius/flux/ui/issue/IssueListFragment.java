package com.riaancornelius.flux.ui.issue;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.BitmapRequest;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.api.ImageSpiceService;
import com.riaancornelius.flux.jira.domain.sprint.report.Issue;
import com.riaancornelius.flux.ui.components.TitledFragment;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @author Riaan
 */
public class IssueListFragment extends Fragment implements TitledFragment {

    private static final String TAG = "IssueListFragment";
    public static String KEY_ALL_ISSUES = "All_issues_fragment_key";
    public static String KEY_PUNTED_ISSUES = "Punted_issues_fragment_key";
    public static String KEY_INCOMPLETE_ISSUES = "Incomplete_issues_fragment_key";
    public static String KEY_COMPLETE_ISSUES = "Completed_issues_fragment_key";

    private IssueAdapter issueAdapter;
    private List<Issue> issues;
    private String title;

    private SpiceManager imageSpiceManager = new SpiceManager(ImageSpiceService.class);

    public static IssueListFragment newInstance(List<Issue> issueList, String title) {
        IssueListFragment fragment = new IssueListFragment();
        fragment.setIssues(issueList);
        fragment.setTitle(title);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_list, container, false);
        if (issues != null) {
            Log.d("FRAGMENT", "Creating view with issues: " + issues.size());
            TextView titleField = (TextView) view.findViewById(R.id.issue_list_header);
            titleField.setText(title);
            issueAdapter = new IssueAdapter(inflater, issues);
            ListView issueList = (ListView) view.findViewById(R.id.issue_list);
            issueList.setAdapter(issueAdapter);
            issueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent itemIntent = new Intent(getActivity(), IssueActivity.class);
                    Object issue = issueAdapter.getItem(position);
                    itemIntent.putExtra(IssueActivity.INTENT_KEY_ISSUE_ID, ((Issue) issue).getKey());
                    startActivity(itemIntent);
                }
            });
            issueAdapter.notifyDataSetChanged();
            loadUserAvatars(issues);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        imageSpiceManager.start(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        imageSpiceManager.shouldStop();
    }

    private void loadUserAvatars(List<Issue> issueList) {
        HashSet<String> users = new HashSet<String>();
        for (Issue issue : issueList) {
            Log.d(TAG, "Adding to list - getAssignee() = " +issue.getAssignee());
            Log.d(TAG, "Adding to list - getAvatarUrl() = " +issue.getAvatarUrl());
            if (issue.getAssignee() != null && issue.getAvatarUrl() != null) {
                users.add(issue.getAssignee() + "|" + issue.getAvatarUrl());
            } else {
                users.add("null" + "|" + "https://secure.gravatar.com/avatar/17f13d9f230593332dba4190eb839037?d=mm&s=48");
            }
        }
        fetchUserAvatars(users);
    }

    private void fetchUserAvatars(HashSet<String> userAvatars) {
        Log.d(TAG, "Processing user avatars: " + userAvatars.size());
        for (String item : userAvatars) {
            Log.d(TAG, "Processing user avatar: " + item);
            String[] split = item.split("\\|");
            Log.d(TAG, Arrays.toString(split));
            Log.d(TAG, "avatar string has items: " + split.length);
            if (split.length == 2) {
                String displayName = split[0];
                String url = split[1];
                Log.d(TAG, "Fetching avatar for " + displayName + " From " + url);
                BitmapRequest imageRequest = new BitmapRequest(url,
                        new File(getActivity().getFilesDir(), displayName + "_avatar.cache"));
                imageSpiceManager.getFromCacheAndLoadFromNetworkIfExpired(imageRequest,
                        "avatar" + displayName, DurationInMillis.ONE_WEEK,
                        new ImageListener(displayName));
            }
        }
    }

    public void setIssues(List<Issue> issuesList) {
        this.issues = issuesList;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    private class ImageListener implements RequestListener<Bitmap> {

        private String assignee;

        private ImageListener(String assignee) {
            this.assignee = assignee;
        }

        @Override
        public void onRequestFailure(SpiceException e) {
            Log.e(TAG, "Could not load image", e);
            Toast.makeText(getActivity(), "Could not load user image: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onRequestSuccess(Bitmap bitmap) {
            Log.d(TAG, "Got user avatar for " + assignee);
            for (Issue issue : issues) {
                if (issue.getAssignee() != null && issue.getAssignee().equals(assignee)) {
                    issue.setUserAvatar(bitmap);
                } else if (issue.getAssignee() == null && assignee.equals("null")) {
                    issue.setUserAvatar(bitmap);
                }
            }
            issueAdapter.notifyDataSetChanged();
        }
    }
}
