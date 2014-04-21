package com.riaancornelius.flux.ui.components;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.XYPlot;
import com.riaancornelius.flux.jira.domain.sprint.burndown.Rate;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author Elsabe
 */
public class BurndownChart extends XYPlot {
    private Double trendLineStart;

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

    }

    public void setStartingPoints(Double value) {
        this.trendLineStart = value;
    }

    public void setTrendLine(List<Rate> rates) {
        //TODO

        // XYSeries trendLine = new SimpleXYSeries();

    }

    private Format dateFormat = new Format() {
        private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");

        @Override
        public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field) {
            Log.d("BURNDOWN - drawing date ", ((Double) object).toString());
            Long timeStamp = ((Double) object).longValue();
            Date date = new Date(timeStamp);
            return dateFormat.format(date, buffer, field);
        }

        @Override
        public Object parseObject(String string, ParsePosition position) {
            return null;
        }
    };


}
