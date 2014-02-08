package com.riaancornelius.flux.ui.issue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.domain.issue.Attachment;

import java.util.ArrayList;

/**
 * @author Elsabe
 */
public class IssueAttachmentsFragment extends Fragment {
    private ArrayList<Attachment> attachments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_attachments, container, false);
        if (attachments != null) {
            AttachmentsAdapter adapter = new AttachmentsAdapter(inflater, attachments);
            ListView attList = (ListView) view.findViewById(R.id.issue_attachment_list);
            attList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        return view;
    }

    public void setAttachments(ArrayList<Attachment> attachments) {
        this.attachments = attachments;
    }
}
