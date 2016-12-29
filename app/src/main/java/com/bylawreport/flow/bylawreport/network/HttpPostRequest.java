package com.bylawreport.flow.bylawreport.network;

import android.os.AsyncTask;

import com.bylawreport.flow.bylawreport.activities.SharedPrefSingleton;
import com.bylawreport.flow.bylawreport.models.Constants;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by DN on 2016-12-24.
 */
public class HttpPostRequest extends AsyncTask<String, Void, Object> {

    private static final int API_URL = 0;
    private static final int POST_DATA_PARAM_INDEX = 1;
    private static final String POST_REQUEST = "POST";
    private static final String CONTENT_TYPE = "application/json; charset=utf-8";
    private static final String BEARER = "Bearer ";



    @Override
    protected String doInBackground(String... params) {
        String url = params[API_URL];
        String data = params[POST_DATA_PARAM_INDEX];
        String result = null;
        String inputLine;
        try {
            //Create a URL object holding our url
            URL myUrl = new URL(url);
            //Create a connection
            HttpURLConnection connect = (HttpURLConnection)
                    myUrl.openConnection();
            connect.setRequestMethod(POST_REQUEST); // POST
            connect.setRequestProperty("Content-Type", CONTENT_TYPE);
            String auth = SharedPrefSingleton.getInstance().getPreferenceByName(Constants.ACCESS_TOKEN.getValue());
            connect.setRequestProperty("Authorization", BEARER + auth); // set authorization from access token in shared prefs.
            //Connect to our url
            //connect.connect();
            OutputStream os = connect.getOutputStream();
            os.write(data.getBytes("UTF-8"));
            os.close();

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
        } catch (Exception e){
            e.printStackTrace();
        }

        return result;

    }

    protected void onPostExecute(String result){
        super.onPostExecute(result);
    }

}
