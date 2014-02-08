package com.riaancornelius.flux.ui.issue;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.jira.domain.sprint.report.Issue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * User: riaan.cornelius
 */
public class IssueAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    private List<Issue> data;

    public IssueAdapter(LayoutInflater i, List<Issue> d) {
        data = d;
        inflater = i;
    }

    public int getCount() {
        return data.size();
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
            vi = inflater.inflate(R.layout.list_row_issue, null);
        }

        TextView firstLine = (TextView) vi.findViewById(R.id.issue_firstLine);
        TextView secondLineId = (TextView) vi.findViewById(R.id.issue_secondLine_left);
        TextView secondLineStatus = (TextView) vi.findViewById(R.id.issue_secondLine_right);
        TextView secondLinePoints = (TextView) vi.findViewById(R.id.issue_secondLine_points);
//        ImageView thumb_image = (ImageView) vi.findViewById(R.id.issue_icon);

        Issue issue = data.get(position);


        // Setting all values in listview
        Log.d("ISSUE", issue.toString());
        firstLine.setText(issue.getSummary());
        secondLineId.setText(issue.getKey());
        secondLineStatus.setText(issue.getStatusName());
        if (issue.getEstimateStatistic() != null &&
                issue.getEstimateStatistic().getStatFieldValue() != null &&
                issue.getEstimateStatistic().getStatFieldValue().getValue() != null) {
            secondLinePoints.setText(Double.toString(issue.getEstimateStatistic().getStatFieldValue().getValue()) + " Points");
        }
//        secondLineStatus.getBackground().mutate().setColorFilter(
//                getColorFromName(issue.getStatus().getStatusCategory().getColorName(), Color.BLACK), PorterDuff.Mode.SRC_ATOP);
//            imageLoader.DisplayImage(song.get(CustomizedListView.KEY_THUMB_URL), thumb_image);
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

}
