package com.bylawreport.flow.bylawreport.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.EditText;

import com.bylawreport.flow.bylawreport.R;
import com.bylawreport.flow.bylawreport.models.UserInformation;
import com.bylawreport.flow.bylawreport.models.ViolationType;
import com.bylawreport.flow.bylawreport.network.RestReportClientUsage;
import com.bylawreport.flow.bylawreport.utilities.FieldValidatorUtil;

import org.json.JSONException;

import java.util.Date;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by DN on 2016-11-20.
 */
public class ReportInformationActivity extends AppCompatActivity {

    private static final String INVALID_EMPTY_FIELD_MESSAGE = "This field cannot be left blank.";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int IMAGE_X_SCALE = 3;
    private static final int IMAGE_Y_SCALE = 3;
    private static final String IMAGE_DATA = "data";

    private ViolationType type;
    private UserInformation userInfo;
    private String incidentDescription = null;
    private String vehicleDescription = null;
    private Date incidentDate = null;
    private ImageView imageView;
    private Display display;
    private Button cameraButton;
    private static boolean isCaptured = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = (ViolationType) getIntent().getSerializableExtra("violationType"); // set the violation type chosen
        userInfo = (UserInformation) getIntent().getSerializableExtra("currentUserInfo");
        setContentView(R.layout.activity_report_info);
        display = getWindowManager().getDefaultDisplay();
        cameraButton = (Button) findViewById(R.id.attach_image_btn);
        setupCameraListener();  // set up camera listener
    }

    public void sendBylawReport(View view) throws JSONException {
        RestReportClientUsage.createBylawReport();
    }

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

            try {
                Bitmap image = (Bitmap) data.getExtras().get(IMAGE_DATA);
                Log.d("Height: ", String.valueOf(image.getHeight()));
                Log.d("Width: ", String.valueOf(image.getWidth()));
                imageView = (ImageView) this.findViewById(R.id.imageView);
                setImageViewDimensions(imageView);
                imageView.setImageBitmap(image);
                setIsCaptured(true);
                updateCaptureImageButton(cameraButton);
            } catch (Exception e) {
                Log.d("IMAGE CAPTURE ERROR: ", e.getMessage());
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


}