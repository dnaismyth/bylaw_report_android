package com.bylawreport.flow.bylawreport.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bylawreport.flow.bylawreport.R;
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
        Log.d("HELLO:", "Main Activity class!");
        setContentView(R.layout.activity_main);
        JSONObject guest = getDefaultGuestUser();
        try {
            Log.d("Hello Guest: ", guest.getJSONObject("data").getString("login"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void beginReport(View view) throws JSONException {
        Intent i = new Intent(getApplicationContext(), ViolationTypeActivity.class);
        startActivity(i);
    }

    /**
     * Return default guest user to be used to create a bylaw report
     * @return
     */
    private JSONObject getDefaultGuestUser() {
        String guestUser = null;
        try {
            guestUser = reportClient.getDefaultGuestUser();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return reportClient.convertResultToJSON(guestUser);
    }
}
