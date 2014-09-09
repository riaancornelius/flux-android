package com.riaancornelius.flux.ui.issue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.domain.issue.Comments;

/**
 * @author Elsabe
 */
public class IssueCommentsFragment extends Fragment {
    Comments commentList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_comments, container, false);
        ListView comments = (ListView) view.findViewById(R.id.issue_comments_list);
        View noComments = view.findViewById(R.id.issue_comments_empty_text);
        if (commentList != null) {
            comments.setVisibility(View.VISIBLE);
            noComments.setVisibility(View.GONE);
            CommentAdapter commentsAdapter = new CommentAdapter(inflater, commentList);
            comments.setAdapter(commentsAdapter);
            commentsAdapter.notifyDataSetChanged();
        } else {
            comments.setVisibility(View.GONE);
            noComments.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public void setComments(Comments commentList) {
        this.commentList = commentList;
    }
}
