package com.bylawreport.flow.bylawreport.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bylawreport.flow.bylawreport.R;

/**
 * Created by DN on 2016-11-20.
 */
public class UserInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
    }

    /**
     * Send user information and begin report
     * @param v
     */
    public void sendUserInformation(View v){
        Intent i = new Intent(getApplicationContext(), ReportInformationActivity.class);
        startActivity(i);
    }

}
