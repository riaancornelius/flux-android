package com.riaancornelius.flux.ui.issue;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.domain.sprint.report.Issue;

import java.util.List;

/**
 * User: riaan.cornelius
 */
public class IssueAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private List<Issue> data;

    private ViewHolder holder;

    public IssueAdapter(LayoutInflater i, List<Issue> d) {
        data = d;
        inflater = i;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.list_row_issue, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.firstLine = (TextView) vi.findViewById(R.id.issue_firstLine);
            viewHolder.secondLineId = (TextView) vi.findViewById(R.id.issue_secondLine_left);
            viewHolder.secondLineStatus = (TextView) vi.findViewById(R.id.issue_secondLine_right);
            viewHolder.secondLinePoints = (TextView) vi.findViewById(R.id.issue_secondLine_points);
            viewHolder.assignedToImage = (ImageView) vi.findViewById(R.id.issue_icon);
            viewHolder.imageProgress = (ProgressBar) vi.findViewById(R.id.loader);
            vi.setTag(viewHolder);
        }

        holder = (ViewHolder) vi.getTag();

        Issue issue = data.get(position);

        // Setting all values in listview
        Log.d("ISSUE", issue.toString());
        holder.firstLine.setText(issue.getSummary());
        holder.secondLineId.setText(issue.getKey());
        holder.secondLineStatus.setText(issue.getStatusName());
        if (issue.getEstimateStatistic() != null &&
                issue.getEstimateStatistic().getStatFieldValue() != null &&
                issue.getEstimateStatistic().getStatFieldValue().getValue() != null) {
            holder.secondLinePoints.setText(Double.toString(issue.getEstimateStatistic().getStatFieldValue().getValue()) + " Points");
        }
//        secondLineStatus.getBackground().mutate().setColorFilter(
//                getColorFromName(issue.getStatus().getStatusCategory().getColorName(), Color.BLACK), PorterDuff.Mode.SRC_ATOP);

        if (issue.getUserAvatar() != null) {
            holder.assignedToImage.setVisibility(View.VISIBLE);
            holder.assignedToImage.setImageBitmap(issue.getUserAvatar());
            holder.imageProgress.setVisibility(View.INVISIBLE);
        }
        return vi;
    }

//    public static Color getColorFromName(String color, int defaultColor) {
//        Color parsedColor;
//
//        Field field = null;
//        try {
//            field = Color.class.getField(color);
//            parsedColor = (Color) field.get(null);
//        } catch (NoSuchFieldException e) {
//            parsedColor = Color.parseColor(Integer.toHexString(defaultColor));
//        } catch (IllegalAccessException e) {
//            parsedColor = defaultColor;
//        }
//        return parsedColor;
//    }

    private class ViewHolder {
        public TextView text;
        public TextView firstLine;
        public TextView secondLineId;
        public TextView secondLineStatus;
        public TextView secondLinePoints;
        public ImageView assignedToImage;
        public ProgressBar imageProgress;
    }
}
