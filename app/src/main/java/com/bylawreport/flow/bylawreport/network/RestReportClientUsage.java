package com.bylawreport.flow.bylawreport.network;

import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.*;
import org.json.*;

import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by DN on 2016-11-26.
 */
public class RestReportClientUsage {

    private static final String CREATE_BYLAW = "/api/reports";
    private static final String GET_S3_CREDENTIALS = "/api/users/s3token";
    private static final String GET_DEFAULT_GUEST_USER = "/api/guests/activate";
    private static final String API_BASE_URL = "http://192.168.1.71:8080";

//    /**
//     * Post to create final bylaw report to send
//     * @throws JSONException
//     */
//    public static void createBylawReport() throws JSONException{
//        HttpGetRequest.post(CREATE_BYLAW_URL, null, new JsonHttpResponseHandler(){
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Log.d("Making post call...", "success!");
//            }
//        });
//    }

    /**
     * Return S3 Credentials to be used for posting images into s3 bucket
     * @throws JSONException
     */
    public void getS3Credentials() {
        HttpGetRequest getRequest = new HttpGetRequest();
        String url = buildApiUrl(GET_S3_CREDENTIALS);
        getRequest.execute(url);
    }

    /**
     * Return default guest user
     */
    public String getDefaultGuestUser() throws ExecutionException, InterruptedException {
        HttpGetRequest getRequest = new HttpGetRequest();
        String url = buildApiUrl(GET_DEFAULT_GUEST_USER);
        String output = getRequest.execute(url).get();
        return output;
    }

    private String buildApiUrl(String URI){
        return API_BASE_URL + URI;
    }
}

