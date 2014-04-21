package com.riaancornelius.flux.ui.sprint;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.riaancornelius.flux.BaseActivity;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.api.request.sprint.BurndownRequest;
import com.riaancornelius.flux.jira.api.request.sprint.SprintReportRequest;
import com.riaancornelius.flux.jira.domain.sprint.Burndown;
import com.riaancornelius.flux.jira.domain.sprint.Sprint;
import com.riaancornelius.flux.jira.domain.sprint.report.SprintReport;
import com.riaancornelius.flux.ui.components.BurndownChart;
import com.riaancornelius.flux.ui.components.TitledFragment;
import com.riaancornelius.flux.ui.issue.IssueListActivity;
import com.riaancornelius.flux.util.DateUtil;

import java.math.BigDecimal;

/**
 * @author Elsabe
 */
public class SprintFragment extends Fragment implements TitledFragment {
    private static final String TAG = "SprintFragment";
    private SpiceManager spiceManager;
    private Sprint sprint;
    private Long boardId;
    private TextView sprintName;
    private TextView sprintDates;
    private Button sprintSummaryTotal;
    private Button sprintSummaryCompleted;
    private Button sprintSummaryUncompleted;
    private Button sprintSummaryPunted;
    private RelativeLayout progress;
    private RelativeLayout error;
    private RelativeLayout burndownLoading;
    private BurndownChart burndownChart;

    public SprintFragment(Sprint s, long boardId, SpiceManager spiceManager) {
        this.spiceManager = spiceManager;
        this.sprint = s;
        this.boardId = boardId;
        this.setRetainInstance(true);
    }

    private void performSprintReportRequest() {
        SprintReportRequest request = new SprintReportRequest(boardId, sprint.getId());
        spiceManager.execute(request, request.createCacheKey(), DurationInMillis.ONE_WEEK, new SprintReportRequestListener());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sprintName.setText(sprint.getName());
        performSprintReportRequest();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Creating fragment view");
        View v = inflater.inflate(R.layout.fragment_sprint_info, container, false);

        sprintName = (TextView) v.findViewById(R.id.sprintName);
        sprintDates = (TextView) v.findViewById(R.id.sprintDates);
        sprintSummaryTotal = (Button) v.findViewById(R.id.totalIssues);
        sprintSummaryCompleted = (Button) v.findViewById(R.id.completedIssues);
        sprintSummaryUncompleted = (Button) v.findViewById(R.id.uncompletedIssues);
        sprintSummaryPunted = (Button) v.findViewById(R.id.puntedIssues);
        progress = (RelativeLayout) v.findViewById(R.id.progress);
        error = (RelativeLayout) v.findViewById(R.id.error);

        burndownLoading = (RelativeLayout) v.findViewById(R.id.loadingBurndown);
        burndownChart = (BurndownChart) v.findViewById(R.id.sprintBurndown);

        sprintName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(getActivity(), IssueListActivity.class);
                myIntent.putExtra(BaseActivity.INTENT_KEY_BOARD_ID, boardId);
                myIntent.putExtra(BaseActivity.INTENT_KEY_SPRINT_ID, sprint.getId());
                getActivity().startActivity(myIntent);
            }
        });

        burndownLoading.setVisibility(View.GONE);
        burndownChart.setVisibility(View.GONE);

        Log.d(TAG, "View created");
        return v;
    }

    private void loadBurndown() {
        burndownLoading.setVisibility(View.VISIBLE);
        BurndownRequest request = new BurndownRequest(boardId, sprint.getId());
        spiceManager.getFromCacheAndLoadFromNetworkIfExpired(request, request.createCacheKey(), DurationInMillis.ONE_HOUR, new BurndownListener());
    }

    @Override
    public String getTitle() {
        return sprint.getName();
    }

    private class SprintReportRequestListener implements RequestListener<SprintReport> {

        @Override
        public void onRequestFailure(SpiceException e) {
            progress.setVisibility(View.GONE);
            error.setVisibility(View.VISIBLE);
            Toast.makeText(getActivity(), "Error during request: " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
        }

        @Override
        public void onRequestSuccess(SprintReport sprintReport) {
            progress.setVisibility(View.GONE);
            //update your UI
            Sprint activeSprint = sprintReport.getSprint();
            if (activeSprint != null) {
                sprintName.setText(activeSprint.getName());
                sprintDates.setText(DateUtil.formatDate(activeSprint.getStartDate())
                        + " - " + DateUtil.formatDate(activeSprint.getEndDate()));
                sprintSummaryTotal.setText(Double.toString(sprintReport.getContents().getAllIssuesEstimateSum().getValue()));
                sprintSummaryCompleted.setText(Double.toString(sprintReport.getContents().getCompletedIssuesEstimateSum().getValue()));
                sprintSummaryUncompleted.setText(Double.toString(sprintReport.getContents().getIncompletedIssuesEstimateSum().getValue()));
                Double puntedTotal = sprintReport.getContents().getPuntedIssuesEstimateSum().getValue();
                if (puntedTotal == null ||
                        new BigDecimal(puntedTotal).compareTo(BigDecimal.ZERO) == 0) {
                    sprintSummaryPunted.setVisibility(View.GONE);
                } else {
                    sprintSummaryPunted.setVisibility(View.VISIBLE);
                    sprintSummaryPunted.setText(Double.toString(puntedTotal));
                }
            }

            burndownChart.setStartingPoints(sprintReport.getContents().getAllIssuesEstimateSum().getValue());
            loadBurndown();

            Log.d("TEST", "Found sprints: " + sprintReport.getSprint().getName());
        }
    }

    private class BurndownListener implements RequestListener<Burndown> {
        @Override
        public void onRequestFailure(SpiceException e) {
            Log.d(TAG, "Could not load burndown chart", e);
            Toast.makeText(getActivity(), "Error during request: " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
            burndownLoading.setVisibility(View.GONE);
        }

        @Override
        public void onRequestSuccess(Burndown burndown) {
            Log.d(TAG, "Successfully loaded burndown info: " + burndown);
            burndownLoading.setVisibility(View.GONE);

            burndownChart.setTimeBounds(burndown.getStartTime(), burndown.getEndTime());
            burndownChart.setTrendLine(burndown.getWorkRateData().getRates());
            burndownChart.setVisibility(View.VISIBLE);
        }
    }

}
