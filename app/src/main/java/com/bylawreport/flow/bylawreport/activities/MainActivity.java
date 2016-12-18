package com.bylawreport.flow.bylawreport.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bylawreport.flow.bylawreport.R;
import com.bylawreport.flow.bylawreport.network.RestReportClientUsage;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final String FUTURA_FONT = "fonts/FuturaLT.ttf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FontOverride.setDefaultFont(this, "DEFAULT", FUTURA_FONT);   // override font, use FuturaLT
        FontOverride.setDefaultFont(this, "MONOSPACE", FUTURA_FONT);   // override font, use FuturaLT
        Log.d("HELLO:", "Main Activity class!");
        setContentView(R.layout.activity_main);
        getDefaultGuestUser();
    }

    public void beginReport(View view) throws JSONException {
        //setContentView(R.layout.activity_violation_type);
        Intent i = new Intent(getApplicationContext(), ViolationTypeActivity.class);
        startActivity(i);
    }

    private String getDefaultGuestUser() {
        RestReportClientUsage usage = new RestReportClientUsage();
        try {
            return usage.getDefaultGuestUser();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
