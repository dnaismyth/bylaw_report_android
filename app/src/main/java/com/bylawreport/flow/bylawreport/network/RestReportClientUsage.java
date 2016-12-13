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
    private static final String GET_S3_CREDENTIALS_URL = "/api/users/s3token";

    /**
     * Post to create final bylaw report to send
     * @throws JSONException
     */
    public static void createBylawReport() throws JSONException{
        ReportClient.post(CREATE_BYLAW_URL, null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Making post call...", "success!");
            }
        });
    }

    /**
     * Return S3 Credentials to be used for posting images into s3 bucket
     * @throws JSONException
     */
    public static void getS3Credentials() throws JSONException {
        ReportClient.get(GET_S3_CREDENTIALS_URL, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("Making get request...", "success!");
            }
        });
    }
}
