package com.bylawreport.flow.bylawreport.network;

import android.util.Log;

import com.loopj.android.http.*;
import org.json.*;

import cz.msebera.android.httpclient.Header;

/**
 * Created by DN on 2016-11-26.
 */
public class RestReportClientUsage {

    private static final String CREATE_BYLAW_URL = "/api/reports";

    public static void createBylawReport() throws JSONException{
        ReportClient.post(CREATE_BYLAW_URL, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Making post call...", "success!");
            }
        });
    }
}
