package com.riaancornelius.flux.ui.issue;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.simple.BitmapRequest;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.api.ImageSpiceService;
import com.riaancornelius.flux.jira.domain.sprint.report.Issue;

import java.io.File;
import java.util.List;

/**
 * User: riaan.cornelius
 */
public class IssueAdapter extends BaseAdapter {

    private static final String TAG = "IssueAdapter";
    private static LayoutInflater inflater = null;
    private List<Issue> data;

    private SpiceManager imageSpiceManager = new SpiceManager(ImageSpiceService.class);

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
            viewHolder.assignedToImage = (ImageView) vi.findViewById(R.id.issue_assigned_image);
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

        if (issue.getAssignee() != null && issue.getAvatarUrl() != null) {
            BitmapRequest imageRequest = new BitmapRequest(issue.getAvatarUrl(),
                    new File(parent.getContext().getFilesDir(), issue.getAssigneeName()+".cache"));
            imageSpiceManager.getFromCacheAndLoadFromNetworkIfExpired(imageRequest,
                    "image"+issue.getAssigneeName(), DurationInMillis.ONE_WEEK, new ImageListener());
        } else {
            BitmapRequest imageRequest = new BitmapRequest(
                    "https://secure.gravatar.com/avatar/17f13d9f230593332dba4190eb839037?d=mm&s=48",
                    new File(parent.getContext().getFilesDir(),"unassigned.cache"));
            imageSpiceManager.getFromCacheAndLoadFromNetworkIfExpired(imageRequest,
                    "imageunassigned", DurationInMillis.ALWAYS_RETURNED, new ImageListener());
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

    private class ImageListener implements RequestListener<Bitmap> {
        @Override
        public void onRequestFailure(SpiceException e) {
            Log.e(TAG, "Could not load image", e);
            holder.imageProgress.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onRequestSuccess(Bitmap bitmap) {
            Log.d(TAG, "Loaded image");
            holder.assignedToImage.setVisibility(View.VISIBLE);
            holder.assignedToImage.setImageBitmap(bitmap);
            holder.imageProgress.setVisibility(View.INVISIBLE);
        }
    }

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
