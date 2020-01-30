package com.example.taxiandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);
    }

    public void passangerSignIn(View view) {
        startActivity(new Intent(
                ChooseModeActivity.this, PassangerSignInActivity.class
        ));
    }

    public void driverSignIn(View view) {
        startActivity(new Intent(
                ChooseModeActivity.this, DriverSignInActivity.class
        ));
    }
}
