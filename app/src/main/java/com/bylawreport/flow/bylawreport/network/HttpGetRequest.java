package com.bylawreport.flow.bylawreport.network;

import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.bylawreport.flow.bylawreport.activities.SharedPrefSingleton;
import com.bylawreport.flow.bylawreport.models.Constants;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGetRequest extends AsyncTask<String, Void, String> {
    private static final int URL_PARAM_INDEX = 0;
    private static final String REQUEST_METHOD = "GET";
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String BEARER = "Bearer ";

    @Override
    protected String doInBackground(String... params){
        String stringUrl = params[URL_PARAM_INDEX] != null ? params[URL_PARAM_INDEX] : null;

        String result;
        String inputLine;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(stringUrl);
            //Create a connection
            HttpURLConnection connect =(HttpURLConnection)
                    myUrl.openConnection();
            connect.setRequestMethod(REQUEST_METHOD); // GET
            connect.setRequestProperty("Content-Type", CONTENT_TYPE);
            String auth = SharedPrefSingleton.getInstance().getPreferenceByName(Constants.ACCESS_TOKEN.getValue());
            connect.setRequestProperty("Authorization", BEARER.concat(auth)); // set authorization from access token in shared prefs.
            //Connect to our url
            connect.connect();
            String responseMessage = connect.getResponseMessage();
            int responseCode = connect.getResponseCode();
            //Create a new InputStreamReader
            InputStreamReader streamReader = new
                    InputStreamReader(connect.getInputStream());
            //Create a new buffered reader and String Builder
            BufferedReader reader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            //Check if the line we are reading is not null
            while((inputLine = reader.readLine()) != null){
                stringBuilder.append(inputLine);
            }
            //Close our InputStream and Buffered reader
            reader.close();
            streamReader.close();
            //Set our result equal to our stringBuilder
            result = stringBuilder.toString();
        }
        catch(IOException e){
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }
}