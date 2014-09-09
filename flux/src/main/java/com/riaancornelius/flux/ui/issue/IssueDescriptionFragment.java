package com.riaancornelius.flux.ui.issue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.domain.issue.Attachment;

import java.util.ArrayList;

import javax.xml.soap.Text;

/**
 * @author Elsabe
 */
public class IssueDescriptionFragment extends Fragment {
    private String description;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_description, container, false);
        TextView descriptionText = (TextView) view.findViewById(R.id.issue_description);
        View noDescription = view.findViewById(R.id.issue_description_empty_text);
        if (description != null && !description.isEmpty()) {
            descriptionText.setVisibility(View.VISIBLE);
            noDescription.setVisibility(View.GONE);
        } else {
            descriptionText.setVisibility(View.GONE);
            noDescription.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
