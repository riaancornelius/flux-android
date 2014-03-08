package com.riaancornelius.flux.ui.sprint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.api.request.sprint.SprintReportRequest;
import com.riaancornelius.flux.jira.domain.sprint.Sprint;
import com.riaancornelius.flux.jira.domain.sprint.report.SprintReport;
import com.riaancornelius.flux.util.DateUtil;

import java.math.BigDecimal;

/**
 * @author Elsabe
 */
public class SprintFragment extends Fragment {
    private SpiceManager spiceManager;
    private Sprint sprint;
    private Long boardId;
    private TextView sprintName;
    private TextView sprintDates;
    private Button sprintSummaryTotal;
    private Button sprintSummaryCompleted;
    private Button sprintSummaryUncompleted;
    private Button sprintSummaryPunted;

    public SprintFragment(Sprint s, long boardId, SpiceManager spiceManager) {
        this.spiceManager = spiceManager;
        this.sprint = s;
        this.boardId = boardId;
        performSprintReportRequest();
    }


    private void performSprintReportRequest() {
        beforeRequest();
        SprintReportRequest request = new SprintReportRequest(boardId, sprint.getId());
        spiceManager.execute(request, request.createCacheKey(), DurationInMillis.ONE_WEEK, new SprintReportRequestListener());
    }

    private void beforeRequest() {
        //TODO
        /**
         * Hide everything except loading thingy
         *
         * TODO: what happens if this get called BEFORE onCreateView?
         */
    }


    private void afterRequest() {
        //TODO
        /**
         * Hide loading thingy
         * Show everything else (what if the sprint did not load?)
         */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sprint_info, container, false);

        sprintName = (TextView) v.findViewById(R.id.sprintName);
        sprintDates = (TextView) v.findViewById(R.id.sprintDates);
        sprintSummaryTotal = (Button) v.findViewById(R.id.totalIssues);
        sprintSummaryCompleted = (Button) v.findViewById(R.id.completedIssues);
        sprintSummaryUncompleted = (Button) v.findViewById(R.id.uncompletedIssues);
        sprintSummaryPunted = (Button) v.findViewById(R.id.puntedIssues);
        return v;
    }

    private class SprintReportRequestListener implements RequestListener<SprintReport> {

        @Override
        public void onRequestFailure(SpiceException e) {
            afterRequest();
            Toast.makeText(getActivity(), "Error during request: " + e.getLocalizedMessage(), Toast.LENGTH_LONG)
                    .show();
        }

        @Override
        public void onRequestSuccess(SprintReport sprintReport) {
            afterRequest();
            //update your UI
            Sprint activeSprint = sprintReport.getSprint();
            if (activeSprint != null) {
                sprintName.setText(activeSprint.getName());
                sprintDates.setText(DateUtil.formatDate(activeSprint.getStartDate())
                        + " - " +
                        DateUtil.formatDate(activeSprint.getEndDate()));
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
            Log.d("TEST", "Found sprints: " + sprintReport.getSprint().getName());
        }
    }

}
