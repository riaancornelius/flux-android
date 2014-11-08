package com.riaancornelius.flux.ui.issue;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.domain.issue.Attachment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elsabe on 2014/02/08.
 */
public class AttachmentsAdapter extends BaseAdapter {
    private final PackageManager packageManager;
    private LayoutInflater inflater;
    private List<Attachment> attachmentList;

    public AttachmentsAdapter(LayoutInflater inflater, List<Attachment> attachments, PackageManager packageManager) {
        this.inflater = inflater;
        this.attachmentList = attachments;
        this.packageManager = packageManager;
    }

    @Override
    public int getCount() {
        return attachmentList.size();
    }

    @Override
    public Object getItem(int i) {
        return attachmentList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        Attachment attachment = attachmentList.get(position);
        View v = view;
        if (view == null) {
            v = inflater.inflate(R.layout.list_row_attachment, null);
        }

        ((TextView) v.findViewById(R.id.author)).setText(attachment.getAuthor().getDisplayName());
        ((TextView) v.findViewById(R.id.attachment_title)).setText(attachment.getFilename());
        ((TextView) v.findViewById(R.id.mimetype)).setText(attachment.getMimeType());

        ImageView imageView = (ImageView) v.findViewById(R.id.fileType);
        imageView.setImageDrawable(loadDrawable(attachment.getMimeType()));
        return v;
    }

    private Drawable loadDrawable(String mimeType) {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setType(mimeType);

        Drawable icon = null;
        final List<ResolveInfo> matches = packageManager.queryIntentActivities(intent, 0);
        for (ResolveInfo match : matches) {
            icon = match.loadIcon(packageManager);
            if (icon != null) {
                return icon;
            }
        }
        return packageManager.getDrawable("com.riaancornelius.flux", android.R.drawable.ic_menu_help, null);
    }
}
