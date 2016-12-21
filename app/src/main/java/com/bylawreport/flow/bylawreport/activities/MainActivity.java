package com.bylawreport.flow.bylawreport.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bylawreport.flow.bylawreport.R;
import com.bylawreport.flow.bylawreport.models.Constants;
import com.bylawreport.flow.bylawreport.network.RestReportClientUsage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private final String FUTURA_FONT = "fonts/FuturaLT.ttf";
    private RestReportClientUsage reportClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportClient = new RestReportClientUsage();
        FontOverride.setDefaultFont(this, "DEFAULT", FUTURA_FONT);   // override font, use FuturaLT
        FontOverride.setDefaultFont(this, "MONOSPACE", FUTURA_FONT);   // override font, use FuturaLT
        setContentView(R.layout.activity_main);
        String token = getDefaultGuestAccessToken();
    }

    public void beginReport(View view) throws JSONException {
        Intent i = new Intent(getApplicationContext(), ViolationTypeActivity.class);
        startActivity(i);
    }

    /**
     * Return default guest user access token
     * @return
     */
    private String getDefaultGuestAccessToken() {
        String token = null;
        String guestUser = reportClient.getDefaultGuestUser();
        JSONObject obj = null;
        try {
            obj = new JSONObject(guestUser);
            token = obj.getString(String.valueOf(Constants.ACCESS_TOKEN));  // store the access token into a variable
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }
}
