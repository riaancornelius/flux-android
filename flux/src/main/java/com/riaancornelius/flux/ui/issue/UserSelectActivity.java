package com.riaancornelius.flux.ui.issue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.riaancornelius.flux.BaseActivity;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.api.request.user.UserRequest;
import com.riaancornelius.flux.jira.domain.author.Author;
import com.riaancornelius.flux.jira.domain.author.AuthorList;


import java.util.ArrayList;

import javax.xml.datatype.Duration;

import roboguice.inject.InjectView;

/**
 * @author Elsabe
 */
public class UserSelectActivity extends BaseActivity {

    public static final String USER_KEY = "UserKey";
    private static final String TAG = "UserSelectActivity";

    @InjectView(R.id.search)
    private ImageButton search;
    @InjectView(R.id.queryValue)
    private EditText queryValue;
    @InjectView(R.id.user_list)
    private ListView userList;

    private UsersAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_select);
        adapter = new UsersAdapter((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), null);
        userList.setAdapter(adapter);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                Author a = (Author) adapter.getItem(index);
                Log.d(TAG, "Selected " + a.getDisplayName());
                returnToPrevious(a.getKey());
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String queryString = queryValue.getText().toString();
                if (queryString.isEmpty()) {
                    Toast.makeText(getApplicationContext(), R.string.enter_value, Toast.LENGTH_LONG).show();
                } else {
                    performSearch(queryString);
                }
            }
        });
    }

    private void performSearch(String queryString) {
        beforeRequest();
        UserRequest request = new UserRequest(queryString);
        spiceManager.getFromCacheAndLoadFromNetworkIfExpired(request, request.getCacheKey(), DurationInMillis.ONE_HOUR, new UserRequestListener());
    }

    protected void returnToPrevious(String userKey) {
        Log.d(TAG, "Returning to previous: " + userKey);
        Intent returnIntent = new Intent();
        returnIntent.putExtra(USER_KEY, userKey);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    public void setupUserList(AuthorList authorList) {
        afterRequest();
        adapter.setAuthors(authorList);
    }

    private class UserRequestListener implements RequestListener<AuthorList> {
        @Override
        public void onRequestFailure(SpiceException e) {
            Toast.makeText(UserSelectActivity.this,
                    "Error during request: " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
            afterRequest();
        }

        @Override
        public void onRequestSuccess(AuthorList authors) {
            setupUserList(authors);
        }
    }
}
