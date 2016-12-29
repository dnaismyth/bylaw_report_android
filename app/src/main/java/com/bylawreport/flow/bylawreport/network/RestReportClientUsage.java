package com.bylawreport.flow.bylawreport.network;

import android.os.AsyncTask;
import android.util.Log;

import com.bylawreport.flow.bylawreport.models.BylawReport;
import com.bylawreport.flow.bylawreport.models.Media;
import com.google.gson.Gson;
import com.loopj.android.http.*;
import org.json.*;

import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by DN on 2016-11-26.
 */
public class RestReportClientUsage {

    private static final String CREATE_BYLAW_REPORT = "/api/reports";
    private static final String GET_S3_CREDENTIALS = "/api/guests/s3token";
    private static final String GET_DEFAULT_GUEST_USER = "/api/guests/activate";
    private static final String API_BASE_URL = "http://192.168.1.71:8080";

    /**
     * Post to create final bylaw report to send
     * @throws JSONException
     */
    public void createBylawReport(BylawReport report, Media media) {
        HttpPostRequest postRequest = new HttpPostRequest();
        ReportAndMediaDTO postData = new ReportAndMediaDTO(report, media);
        Gson gson = new Gson();
        String jsonData = gson.toJson(postData);
        String url = buildApiUrl(CREATE_BYLAW_REPORT);
        try {
            postRequest.execute(url, jsonData ).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * Return S3 Credentials to be used for posting images into s3 bucket
     * @throws JSONException
     */
    public String getS3Credentials() {
        HttpGetRequest getRequest = new HttpGetRequest();
        String url = buildApiUrl(GET_S3_CREDENTIALS);
        String credentials = null;
        try {
            Log.d("REPORT_CLIENT: ", "requesting S3 credentials...");
            credentials = getRequest.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return credentials;
    }

    /**
     * Return default guest user
     */
    public String getDefaultGuestUser()  {
        HttpGetRequest getRequest = new HttpGetRequest();
        String url = buildApiUrl(GET_DEFAULT_GUEST_USER);
        String output = null;
        try {
            output = getRequest.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return output;
    }

    private String buildApiUrl(String URI){
        return API_BASE_URL + URI;
    }

    /**
     * Convert result to JSON Object
     * @param result
     * @return
     */
    public JSONObject convertResultToJSON(String result){
        JSONObject obj = null;
        if(result != null) {
            try {
                obj = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

}

