package com.riaancornelius.flux.ui.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.*;
import com.riaancornelius.flux.BaseActivity;
import com.riaancornelius.flux.R;
import com.riaancornelius.flux.domain.Settings;
import com.riaancornelius.flux.ui.components.EncryptedEditText;
import roboguice.inject.InjectView;

/**
 * User: riaan.cornelius
 */
public class SettingsActivity extends BaseActivity
        implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @InjectView(R.id.username_entry_field) EditText usernameField;
    @InjectView(R.id.password_entry_field) EncryptedEditText passwordField;
    @InjectView(R.id.showme) CheckBox showPassword;
    @InjectView(R.id.save) Button SaveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        showPassword.setOnCheckedChangeListener(this);
        SaveButton.setOnClickListener(this);

        loadUserSettings(getJiraSharedPreferences());
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            passwordField.setTransformationMethod(null);
        } else {
            passwordField.setTransformationMethod(new PasswordTransformationMethod());
        }
    }

    @Override
    public void onClick(View v) {
        SharedPreferences settings = super.getJiraSharedPreferences();
        if (v.getId()==R.id.save) {
            String username = usernameField.getText().toString();
            String encodedPassword = passwordField.getText(true).toString();
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(Settings.JIRA_USERNAME_KEY, username);
            editor.putString(Settings.JIRA_PASSWORD_KEY, encodedPassword);
            editor.commit();
            Toast.makeText(SettingsActivity.this, "User details saved for " + username, Toast.LENGTH_LONG).show();
            super.loadMainActivity();
        }
    }

    private void loadUserSettings(SharedPreferences settings) {
        String username = settings.getString(Settings.JIRA_USERNAME_KEY, "");
        String encodedPassword = settings.getString(Settings.JIRA_PASSWORD_KEY, "");
        usernameField.setText(username);
        passwordField.setText(encodedPassword, true);
    }
}
