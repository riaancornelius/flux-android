package com.riaancornelius.flux.ui.settings;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

import com.riaancornelius.flux.R;

/**
 * Created by riaan.cornelius on 2014/03/06.
 */
public class JiraBoardPreferences extends DialogPreference {

    public JiraBoardPreferences(Context context, AttributeSet attrs) {
        super(context, attrs);

        setDialogLayoutResource(R.layout.jira_board_preference_dialog);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
    }

}
