package com.bylawreport.flow.bylawreport.network;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * Created by DN on 2016-11-25.
 * Report Client to communicate with REST API
 */
public class ReportClient {
    private static final String API_BASE_URL = "http://192.168.1.64:8080";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public ReportClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    // Method to create a bylaw report
    public static void post(final String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
            String absoluteUrl = getAbsoluteUrl(url);
            client.post(absoluteUrl, params, responseHandler);
    }

    // Create api url
    private static String getAbsoluteUrl(String url){
        return API_BASE_URL.concat(url);
    }

}
