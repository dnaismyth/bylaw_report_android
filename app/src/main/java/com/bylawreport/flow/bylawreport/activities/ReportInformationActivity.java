package com.bylawreport.flow.bylawreport.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.EditText;

import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bylawreport.flow.bylawreport.R;
import com.bylawreport.flow.bylawreport.models.Constants;
import com.bylawreport.flow.bylawreport.models.UserInformation;
import com.bylawreport.flow.bylawreport.models.ViolationType;
import com.bylawreport.flow.bylawreport.network.RestReportClientUsage;
import com.bylawreport.flow.bylawreport.utilities.FieldValidatorUtil;
import com.bylawreport.flow.bylawreport.utilities.S3Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.UUID;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by DN on 2016-11-20.
 */
public class ReportInformationActivity extends AppCompatActivity {

    private static final String INVALID_EMPTY_FIELD_MESSAGE = "This field cannot be left blank.";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int IMAGE_X_SCALE = 3;
    private static final int IMAGE_Y_SCALE = 3;
    private static final String IMAGE_DATA = "data";

    private RestReportClientUsage reportClient;

    private ViolationType type;
    private UserInformation userInfo;
    private String incidentDescription = null;
    private String vehicleDescription = null;
    private Date incidentDate = null;
    private ImageView imageView;
    private Display display;
    private Button cameraButton;
    private static boolean isCaptured = false;
    private String token;
    private S3Util s3Util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reportClient = new RestReportClientUsage();
        type = (ViolationType) getIntent().getSerializableExtra(Constants.VIOLATION_TYPE.getValue()); // set the violation type chosen
        userInfo = (UserInformation) getIntent().getSerializableExtra(Constants.REPORTER_INFO.getValue());
        setContentView(R.layout.activity_report_info);
        display = getWindowManager().getDefaultDisplay();
        cameraButton = (Button) findViewById(R.id.attach_image_btn);
        setupCameraListener();  // set up camera listener
    }

//    public void sendBylawReport(View view) throws JSONException {
//        RestReportClientUsage.createBylawReport();
//    }

    public boolean isCaptured() {
        return isCaptured;
    }

    public void setIsCaptured(boolean isCaptured) {
        this.isCaptured = isCaptured;
    }

    private void reportDescription() {
        EditText description = (EditText) findViewById(R.id.report_description);
        description.addTextChangedListener(new FieldValidatorUtil(description) {
            @Override
            public void validate(EditText editText, Object text) {
                if (!isValidFieldText((String) text)) {
                    editText.setError(INVALID_EMPTY_FIELD_MESSAGE);
                } else {
                    incidentDescription = (String) text;
                }
            }
        });
    }

    private void vehicleDescription() {
        EditText vehicle_description = (EditText) findViewById(R.id.vehicle_description);
        vehicle_description.addTextChangedListener(new FieldValidatorUtil(vehicle_description) {
            @Override
            public void validate(EditText editText, Object text) {
                if (!isValidFieldText((String) text)) {
                    editText.setError(INVALID_EMPTY_FIELD_MESSAGE);
                } else {
                    vehicleDescription = (String) text;
                }
            }
        });
    }

    private void incidentDate() {
        EditText incident_date = (EditText) findViewById(R.id.incident_date);
        incident_date.addTextChangedListener(new FieldValidatorUtil(incident_date) {
            @Override
            public void validate(EditText editText, Object date) {
                if (date == null) {
                    editText.setError(INVALID_EMPTY_FIELD_MESSAGE);
                } else {
                    incidentDate = (Date) date;
                }
            }
        });
    }

    /**
     * Set up listener for image capture
     */
    private void setupCameraListener() {
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
            }
        });
    }

    /**
     * Camera Activity result
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            String creds = reportClient.getS3Credentials();
            try {
                Bitmap image = (Bitmap) data.getExtras().get(IMAGE_DATA);
                imageView = (ImageView) this.findViewById(R.id.imageView);
                setImageViewDimensions(imageView);
                imageView.setImageBitmap(image);
                setIsCaptured(true);

                File toUpload = convertBitmapToFile(image, "testing");
                storeS3Credentials();
                s3Util = new S3Util();
                String s3Key = buildS3ImageKey();   // build unique key to store image in s3
                PutObjectRequest por = buildS3PutRequest(s3Key, toUpload);
                Log.d("Put Request", "executing...");
                s3Util.execute(por);
                updateCaptureImageButton(cameraButton);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Format image view after an image has been captured
     *
     * @param iv
     */
    private void setImageViewDimensions(ImageView iv) {
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width / IMAGE_X_SCALE, height / IMAGE_Y_SCALE);
        iv.setLayoutParams(params);
    }


    /**
     * Update the text/style of the capture image button depending on
     * image capture.
     *
     * @param button
     */
    private void updateCaptureImageButton(Button button) {
        cameraButton.setText("Tap to take a new image.");
        //cameraButton.setBackgroundColor();
    }

    private void storeS3Credentials() {
        String secretKey = null;
        String accessKey = null;
        String s3Bucket = null;
        String sessionToken = null;
        String credentials = reportClient.getS3Credentials();
        JSONObject obj = null;
        try {
            obj = new JSONObject(credentials);
            accessKey = obj.getString(Constants.S3_ACCESS_KEY.getValue());
            secretKey = obj.getString(Constants.S3_SECRET_KEY.getValue());
            sessionToken = obj.getString(Constants.S3_SESSION_TOKEN.getValue());
            s3Bucket = obj.getString(Constants.S3_BUCKET.getValue());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Store the credentials into shared preferences
        SharedPrefSingleton.getInstance().writePreference(Constants.S3_ACCESS_KEY.getValue(), accessKey);
        SharedPrefSingleton.getInstance().writePreference(Constants.S3_SECRET_KEY.getValue(), secretKey);
        SharedPrefSingleton.getInstance().writePreference(Constants.S3_SESSION_TOKEN.getValue(), sessionToken);
        SharedPrefSingleton.getInstance().writePreference(Constants.S3_BUCKET.getValue(), s3Bucket);

    }

    private File convertBitmapToFile(Bitmap map, String fileName){
        File f = new File(this.getCacheDir(), fileName);
        try {
            f.createNewFile();

            //Convert bitmap to byte array
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            map.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();

            //write the bytes in file
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (Exception e){
            Log.d("S3_UTIL", "Error writing file from bitmap.");
        }
        return f;
    }

    private PutObjectRequest buildS3PutRequest(String s3Key, File toUpload){
        Log.d("Put Request", "building...");
        String s3Bucket = SharedPrefSingleton.getInstance().getPreferenceByName(Constants.S3_BUCKET.getValue());
        PutObjectRequest por = new PutObjectRequest(s3Bucket, s3Key, toUpload);
        return por;
    }

    private String buildS3ImageKey(){
        return type.name() + "/report_" + UUID.randomUUID().toString();
    }

}