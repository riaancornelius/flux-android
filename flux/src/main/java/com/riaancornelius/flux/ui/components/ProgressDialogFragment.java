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
    private String title = "";
    private String message = "";

    protected String getTitle() {
        return title;
    }

    protected void setTitle(String title) {
        this.title = title;
    }

    protected String getMessage() {
        return message;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    public static ProgressDialogFragment newInstance(String title, String message) {
        ProgressDialogFragment frag = new ProgressDialogFragment();
        frag.setTitle(title);
        frag.setMessage(message);
        frag.setRetainInstance(true);
        frag.setShowsDialog(true);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setTitle(getTitle());
        dialog.setMessage(getMessage());
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
