package com.example.ninjareader.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.example.ninjareader.R;
import com.parse.ParseTwitterUtils;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

public class LoginActivity extends ActionBarActivity {

    public static final int PARSE_LOGIN_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ParseUser.getCurrentUser() == null) {
            login();
        } else {
            onLoginSuccess();
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
                Toast.makeText(LoginActivity.this, "Login was canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onLoginSuccess() {
        Intent i = new Intent(this, ReadingListActivity.class);
        startActivity(i);
    }
}
