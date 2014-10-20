package com.riaancornelius.flux.ui.components;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import com.androidplot.series.XYSeries;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.riaancornelius.flux.jira.domain.sprint.Burndown;
import com.riaancornelius.flux.jira.domain.sprint.burndown.BurndownChange;
import com.riaancornelius.flux.jira.domain.sprint.burndown.Rate;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


/**
 * @author Elsabe
 */
public class BurndownChart extends XYPlot {
    private Double startingStoryPoints;
    private Long startTime;
    private Long endTime;
    private Long completeTime;

    public BurndownChart(Context context, String s) {
        super(context, s);
        init();
    }

    public BurndownChart(Context context, String s, RenderMode renderMode) {
        super(context, s, renderMode);
        init();
    }

    public BurndownChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public BurndownChart(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        setPadding(5, 5, 5, 5);

        //format graph
        getBackgroundPaint().setColor(Color.WHITE);
        getGraphWidget().getBackgroundPaint().setColor(Color.WHITE);
        getGraphWidget().getGridBackgroundPaint().setColor(Color.WHITE);
        getGraphWidget().getGridLinePaint().setColor(getResources().getColor(com.riaancornelius.flux.R.color.light_gray));

        getLayoutManager().remove(getLegendWidget());
        getLayoutManager().remove(getDomainLabelWidget());

        setTitle(getContext().getString(com.riaancornelius.flux.R.string.burndown_chart));
        //setup x axis
        setDomainLabel("");
        setDomainValueFormat(dateFormat);
        //setup y axis
        setRangeLabel("");
    }

    public void setTimeBounds(Long startTime, Long endTime, Long completeTime) {
        Log.d("BURNDOWN", startTime + " - " + endTime);
        setDomainBoundaries(new Double(startTime), new Double(endTime), BoundaryMode.AUTO);
        Log.d("BURNDOWN", "Converted start date " + new Date(startTime) + " to " + new Date(new Double(startTime).longValue()));
        Log.d("BURNDOWN", "Converted end date " + new Date(endTime) + " to " + new Date(new Double(endTime).longValue()));
        this.startTime = startTime;
        this.endTime = endTime;
        this.completeTime = completeTime;
    }

    public void setStartingPoints(Double value) {
        this.startingStoryPoints = value;
    }

    public void setTrendLine(List<Rate> rates) {
        Log.d("TRENDLINE", "Trendline data: " + rates);

        //make sure rates are ordered
        Collections.sort(rates, new Comparator<Rate>() {
            @Override
            public int compare(Rate lhs, Rate rhs) {
                return lhs.getStart() < rhs.getStart() ? -1 : (lhs.getStart() == rhs.getStart() ? 0 : 1);
            }
        });

        int slantedLines = 0;
        for (Rate r : rates) {
            if (r.getRate() > 0) {
                slantedLines++;
            }
        }
        Log.d("BURNDOWN - TRENDLINE", "Starting points: " + startingStoryPoints);
        Log.d("BURNDOWN - TRENDLINE", "Number of downs: " + slantedLines);

        double rateChange = startingStoryPoints / slantedLines; // "slope" of slanted lines
        double runningTotal = startingStoryPoints;
        for (Rate r : rates) {
            Log.d("BURNDOWN - TRENDLINE",
                    "Time: " + r.getStart() + " - " + r.getEnd() +
                            ", rate: " + r.getRate() +
                            ", starting story points: " + runningTotal);

            //Draw this change
            List<Double> storyPoints;
            if (r.getRate() > 0) {
                storyPoints = Arrays.asList(runningTotal, runningTotal - rateChange);
                runningTotal -= rateChange;
            } else {
                storyPoints = Arrays.asList(runningTotal, runningTotal);
            }
            XYSeries line = new SimpleXYSeries(Arrays.asList(r.getStart(), r.getEnd()), storyPoints, "");
            LineAndPointFormatter formatter = new LineAndPointFormatter(Color.LTGRAY, Color.TRANSPARENT, Color.TRANSPARENT);
            this.addSeries(line, formatter);
        }

    }

    private Format dateFormat = new Format() {
        private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");

        @Override
        public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field) {
            Log.d("BURNDOWN - DATE", ((Double) object).toString());
            Double timeStamp = ((Double) object);
            Log.d("BURNDOWN - DATE", "Drawing timestamp " + timeStamp.longValue());
            Date date = new Date(timeStamp.longValue());
            Log.d("BURNDOWN - DATE", "Converted time stamp: " + date);
            return dateFormat.format(date, buffer, field);
        }

        @Override
        public Object parseObject(String string, ParsePosition position) {
            return null;
        }
    };


    public void setBurnDown(Map<Long, Burndown.ChangeList> changes) {
        Map<Long, Burndown.ChangeList> sortedMap = new TreeMap<Long, Burndown.ChangeList>(changes);
        Map<String, Double> storyPointValues = new HashMap<String, Double>();

        Set<String> startingStories = new HashSet<String>();

        Map<Long, StoryChange> sprintChanges = new TreeMap<Long, StoryChange>();

        for (Long key : sortedMap.keySet()) {
            Burndown.ChangeList changeList = sortedMap.get(key);

            //reduce timestamps down to day
            // Long time = DateUtils.truncate(new Date(key), Calendar.DATE).getTime();

            Log.d("BURNDOWN - BURNDOWN", new Date(key) + " - " + changeList);
            for (BurndownChange change : changeList) {
                if (change.getStatC() != null) {
                    storyPointValues.put(change.getKey(), change.getStatC().getNewValue());
                }
                if (key < startTime) {
                    startingStories.add(change.getKey());
                } else if (change.getColumn() != null) {
                    if (change.getColumn().getNotDone()) {
                        //new one added to sprint
                        sprintChanges.put(key, new StoryChange(change.getKey(), true));
                    } else if (change.getColumn().getDone()) {
                        // story done
                        sprintChanges.put(key, new StoryChange(change.getKey(), false));
                    }
                }
            }
        }

        Log.d("BURNDOWN - CALCS", "Story point values: " + storyPointValues);

        //calculate starting points
        double runningCount = 0;
        for (String story : startingStories) {
            Log.d("BURNDOWN - CALCS", "Looking up " + story);
            Double storyPoints = storyPointValues.get(story);
            runningCount += storyPoints != null ? storyPoints : 0;
        }

        Log.d("BURNDOWN - CALCS", new Date(startTime) + " starting stories(" + runningCount + " story points): " + startingStories);


        LineAndPointFormatter formatter = new LineAndPointFormatter(Color.RED, Color.TRANSPARENT, Color.TRANSPARENT);

        if (runningCount > startingStoryPoints) {
            XYSeries line = new SimpleXYSeries(Arrays.asList(startTime, startTime), Arrays.asList(startingStoryPoints, runningCount), "");
            this.addSeries(line, formatter);
        }

        Long lastTimeStamp = startTime;
        for (Long key : sprintChanges.keySet()) {
            //draw a straight line in between
            XYSeries horizontalLine = new SimpleXYSeries(Arrays.asList(lastTimeStamp, key), Arrays.asList(runningCount, runningCount), "");
            this.addSeries(horizontalLine, formatter);

            StoryChange change = sprintChanges.get(key);
            Double storyValue = storyPointValues.get(change.key);
            Double changeValue = (storyValue != null ? storyValue : 0) * (change.added ? 1 : -1);
            double newValue = runningCount + changeValue;

            //draw the story point change
            XYSeries verticalLine = new SimpleXYSeries(Arrays.asList(key, key), Arrays.asList(runningCount, newValue), "");
            this.addSeries(verticalLine, formatter);

            lastTimeStamp = key;
            runningCount = newValue;
        }

        if (completeTime == 0) {
            completeTime = Calendar.getInstance().getTimeInMillis();
        }

        if (lastTimeStamp < completeTime) {
            XYSeries horizontalLine = new SimpleXYSeries(Arrays.asList(lastTimeStamp, completeTime), Arrays.asList(runningCount, runningCount), "");
            this.addSeries(horizontalLine, formatter);
        }
    }

    private class StoryChange {
        private final String key;
        private final boolean added;

        public StoryChange(String key, boolean added) {
            this.key = key;
            this.added = added;
        }
    }
}
