package com.riaancornelius.flux.ui.issue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.domain.sprint.report.Issue;
import com.riaancornelius.flux.ui.components.TitledFragment;

import java.util.List;

/**
 * @author Riaan
 */
public class IssueListFragment extends Fragment implements TitledFragment {

    public static String KEY_ALL_ISSUES = "All_issues_fragment_key";
    public static String KEY_PUNTED_ISSUES = "Punted_issues_fragment_key";
    public static String KEY_INCOMPLETE_ISSUES = "Incomplete_issues_fragment_key";
    public static String KEY_COMPLETE_ISSUES = "Completed_issues_fragment_key";

    private List<Issue> issues;
    private String title;

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
            IssueAdapter issueAdapter = new IssueAdapter(inflater, issues);
            ListView issueList = (ListView) view.findViewById(R.id.issue_list);
            issueList.setAdapter(issueAdapter);
            issueAdapter.notifyDataSetChanged();
        }
        return view;
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
}
