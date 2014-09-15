package com.riaancornelius.flux.ui.issue;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.domain.issue.Comment;
import com.riaancornelius.flux.jira.domain.issue.Comments;

import java.text.DateFormat;


/**
 * User: riaan.cornelius
 */
public class CommentAdapter extends BaseAdapter {

    public static final int COMMENT_ITEM_TAG = "comment_item_data".hashCode();

    private static LayoutInflater inflater = null;
    private Comments data;

    public CommentAdapter(LayoutInflater i, Comments d) {
        data = d;
        inflater = i;
    }

    public int getCount() {
        return data.getComments().size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.list_row_comment, null);
        }

        Comment comment = data.getComments().get(position);
        vi.setTag(COMMENT_ITEM_TAG, comment);

        TextView firstLine = (TextView) vi.findViewById(R.id.comment_firstLine);
        TextView secondLine = (TextView) vi.findViewById(R.id.comment_secondLine);
//        ImageView thumb_image = (ImageView) vi.findViewById(R.id.comment_icon);

        // Setting all values in listview
        Log.d("COMMENT", comment.toString());
        Log.d("COMMENT BODY" , comment.getBody());
        firstLine.setText(comment.getBody());
        secondLine.setText(comment.getAuthor().getDisplayName() +
                " ("+ DateFormat.getDateInstance().format(comment.getCreated()) +")");
//            imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
        return vi;
    }
}
