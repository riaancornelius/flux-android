package com.riaancornelius.flux.ui.issue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.riaancornelius.flux.jira.domain.author.Author;
import com.riaancornelius.flux.jira.domain.issue.Comments;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Elsabe on 2014/02/08.
 */
public class UsersAdapter extends BaseAdapter {
    private List<Author> authors;
    private LayoutInflater inflater;

    public UsersAdapter(LayoutInflater i, List<Author> authorList) {
        this.inflater = i;
        setAuthors(authorList);
    }

    public void setAuthors(List<Author> authorList) {
        if (authorList == null) {
            authors = new ArrayList<Author>();
        } else {
            authors = authorList;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return authors.size();
    }

    @Override
    public Object getItem(int i) {
        return authors.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Author author = authors.get(i);

        View v = view;
        if (v == null) {
            v = inflater.inflate(android.R.layout.simple_list_item_2, null);
        }
        ((TextView) v.findViewById(android.R.id.text1)).setText(author.getDisplayName());
        ((TextView) v.findViewById(android.R.id.text2)).setText(author.getEmailAddress());
        return v;
    }
}
