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
import com.riaancornelius.flux.jira.domain.sprint.burndown.Rate;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


/**
 * @author Elsabe
 */
public class BurndownChart extends XYPlot {
    private Double trendLineStartStoryPoints;
    private Long startTime;
    private Long endTime;

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

        setTitle(getContext().getString(com.riaancornelius.flux.R.string.burndown_chart));
        //setup x axis
        setDomainLabel("");
        setDomainValueFormat(dateFormat);
        //setup y axis
        setRangeLabel("");
    }

    public void setTimeBounds(Long startTime, Long endTime) {
        Log.d("BURNDOWN", startTime + " - " + endTime);
        setDomainBoundaries(new Double(startTime), new Double(endTime), BoundaryMode.AUTO);
        Log.d("BURNDOWN", "Converted start date " + new Date(startTime) + " to " + new Date(new Double(startTime).longValue()));
        Log.d("BURNDOWN", "Converted end date " + new Date(endTime) + " to " + new Date(new Double(endTime).longValue()));
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setStartingPoints(Double value) {
        this.trendLineStartStoryPoints = value;
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
        Log.d("BURNDOWN - TRENDLINE", "Starting points: " + trendLineStartStoryPoints);
        Log.d("BURNDOWN - TRENDLINE", "Number of downs: " + slantedLines);

        double rateChange = trendLineStartStoryPoints / slantedLines; // "slope" of line

        Long[] dates = new Long[rates.size() + 1];
        Double[] values = new Double[rates.size() + 1];
        //first entry is
        dates[0] = rates.get(0).getStart();
        values[0] = trendLineStartStoryPoints;

        double currentValue = trendLineStartStoryPoints;
        int currentIndex = 1;
        for (Rate r : rates) {
            dates[currentIndex] = r.getEnd();
            if (r.getRate() > 0) {
                currentValue -= rateChange;
            }
            values[currentIndex] = currentValue;
            currentIndex++;
        }
        Log.d("TRENDLINE", "dates: " + dates);
        Log.d("TRENDLINE", "values: " + values);

        XYSeries trendLine = new SimpleXYSeries(Arrays.asList(dates), Arrays.asList(values), "Trendline");
        LineAndPointFormatter formatter = new LineAndPointFormatter(Color.RED, Color.RED, Color.RED);

        this.addSeries(trendLine, formatter);

    }

    private Format dateFormat = new Format() {
        private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");

        @Override
        public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field) {
            Log.d("BURNDOWN - DATE", ((Double) object).toString());
            Double timeStamp = ((Double) object);
            Log.d("BURNDOWN - DATE", "Drawing timestamp "+ timeStamp.longValue());
            Date date = new Date(timeStamp.longValue());
            Log.d("BURNDOWN - DATE","Converted time stamp: " + date);
            return dateFormat.format(date, buffer, field);
        }

        @Override
        public Object parseObject(String string, ParsePosition position) {
            return null;
        }
    };


}
