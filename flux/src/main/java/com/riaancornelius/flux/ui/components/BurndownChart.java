package com.riaancornelius.flux.ui.components;

import android.content.Context;
import android.util.AttributeSet;

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
        //setup x axis
        setTitle(getContext().getString(com.riaancornelius.flux.R.string.burndown_chart));
        setDomainLabel("");
        setDomainValueFormat(dateFormat);

    }

    public void setBounds(Long startTime, Long endTime) {
        setDomainBoundaries(startTime / 1000.0, endTime / 1000.0, BoundaryMode.AUTO);
    }

    public void setTrendLine(List<Rate> rates) {
        //TODO
        //throw new UnsupportedOperationException();
    }

    private Format dateFormat = new Format() {
        private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM");

        @Override
        public StringBuffer format(Object object, StringBuffer buffer, FieldPosition field) {
            Long timestamp = ((Number) object).longValue() * 1000;
            Date date = new Date(timestamp);
            return dateFormat.format(date, buffer, field);
        }

        @Override
        public Object parseObject(String string, ParsePosition position) {
            return null;
        }
    };
}
