package com.riaancornelius.flux.ui.sprint;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.riaancornelius.flux.BaseActivity;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.api.SpiceCallback;
import com.riaancornelius.flux.jira.api.request.sprint.SprintsRequest;
import com.riaancornelius.flux.jira.domain.sprint.Sprint;
import com.riaancornelius.flux.jira.domain.sprint.Sprints;

/**
 * User: riaan.cornelius
 */
public class SprintsActivity extends BaseActivity implements SpiceCallback {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sprints);

        initUIComponents();
    }

    private ArrayAdapter<String> sprintsAdapter;

    private String lastRequestCacheKey;

    private void initUIComponents() {
        ListView sprintsList = (ListView) findViewById(R.id.search_results);

        long boardId = getIntent().getExtras().getLong(BaseActivity.INTENT_KEY_BOARD_ID);

        sprintsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1);
        sprintsList.setAdapter(sprintsAdapter);

        performRequest(boardId);

//        searchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                performRequest(1l);
//                // clear focus
//                LinearLayout linearLayout = (LinearLayout) findViewById(R.id.search_layout);
//                linearLayout.requestFocus();
//            }
//        });
    }

    private void performRequest(Long board) {
        beforeRequest();

        SprintsRequest request = new SprintsRequest(board, false);
        lastRequestCacheKey = request.createCacheKey();
        spiceManager.execute(request, lastRequestCacheKey,
                DurationInMillis.ONE_MINUTE, new ListSprintsRequestListener());
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
            spiceManager.addListenerIfPending(Sprints.class,
                    lastRequestCacheKey, new ListSprintsRequestListener());
            spiceManager.getFromCache(Sprints.class,
                    lastRequestCacheKey, DurationInMillis.ONE_WEEK,
                    new ListSprintsRequestListener());
        }
    }

    @Override
    public void onRequestFinished() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private class ListSprintsRequestListener implements
            RequestListener<Sprints> {
        @Override
        public void onRequestFailure(SpiceException e) {
            SprintsActivity.this.afterRequest();
            Toast.makeText(SprintsActivity.this,
                    "Error during request: " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
            SprintsActivity.this.setProgressBarIndeterminateVisibility(false);
        }

        @Override
        public void onRequestSuccess(Sprints sprints) {
            // sprints could be null just if contentManager.getFromCache(...)
            // doesn't return anything.
            if (sprints == null) {
                return;
            }

            sprintsAdapter.clear();

            for (Sprint sprint : sprints.getSprints()) {
                sprintsAdapter.add(sprint.getName());
            }

            sprintsAdapter.notifyDataSetChanged();

            SprintsActivity.this.afterRequest();
        }
    }
}
