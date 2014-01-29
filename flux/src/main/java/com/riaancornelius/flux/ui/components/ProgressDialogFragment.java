package com.riaancornelius.flux.ui.components;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;

/**
 * @author Elsabe
 */
public class ProgressDialogFragment extends DialogFragment {
    private String title;
    private String message;

    private ProgressDialogFragment(String t, String m) {
        title = t;
        message = m;
    }

    public static ProgressDialogFragment newInstance(String title, String message) {
        return new ProgressDialogFragment(title, message);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setOnKeyListener(new BackButtonKeyListener());
        return dialog;
    }

    private static class BackButtonKeyListener implements DialogInterface.OnKeyListener {
        /**
         * If this is the back button, ignore it. Otherwise allow th next method in the chain to handle it.
         */
        @Override
        public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                return true;
            }
            return false;
        }
    }
}
