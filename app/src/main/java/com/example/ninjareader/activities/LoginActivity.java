package com.example.ninjareader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.ninjareader.R;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

public class LoginActivity extends ActionBarActivity {

    public static final int PARSE_LOGIN_REQUEST_CODE = 42;
    private String sharedUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }

        if (ParseUser.getCurrentUser() == null) {
            login();
        } else {
            onLoginSuccess();
        }
    }

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            sharedUrl = sharedText;
        }
    }

    private void login() {
        ParseTwitterUtils.initialize(getResources().getString(R.string.twitter_app_key), getResources().getString(R.string.twitter_app_secret));
        ParseLoginBuilder builder = new ParseLoginBuilder(LoginActivity.this);
        startActivityForResult(builder.build(), PARSE_LOGIN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PARSE_LOGIN_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                onLoginSuccess();
            } else if (resultCode == RESULT_CANCELED) {
                // This exits the app
                finish();
            }
        }
    }

    private void onLoginSuccess() {
        Intent i = new Intent(this, ReadingListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // To clean up all activities
        // if sharedUrl is set, pass it to activity
        if (sharedUrl != null) {
            i.putExtra("shared_url", sharedUrl);
        }
        startActivity(i);
        finish();
    }
}
