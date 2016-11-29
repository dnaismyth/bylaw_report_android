package com.bylawreport.flow.bylawreport.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.bylawreport.flow.bylawreport.R;
import com.bylawreport.flow.bylawreport.models.UserInformation;
import com.bylawreport.flow.bylawreport.models.ViolationType;
import com.bylawreport.flow.bylawreport.network.RestReportClientUsage;
import com.bylawreport.flow.bylawreport.utilities.FieldValidatorUtil;

import org.json.JSONException;

import java.util.Date;

/**
 * Created by DN on 2016-11-20.
 */
public class ReportInformationActivity extends AppCompatActivity {

    private ViolationType type;
    private UserInformation userInfo;
    private static final String INVALID_EMPTY_FIELD_MESSAGE = "This field cannot be left blank.";
    private String incidentDescription = null;
    private String vehicleDescription = null;
    private Date incidentDate = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        type = (ViolationType)getIntent().getSerializableExtra("violationType"); // set the violation type chosen
        userInfo = (UserInformation)getIntent().getSerializableExtra("currentUserInfo");
        setContentView(R.layout.activity_report_info);
    }

    public void sendBylawReport(View view) throws JSONException {
        RestReportClientUsage.createBylawReport();
    }

    private void reportDescription(){
        EditText description = (EditText) findViewById(R.id.report_description);
        description.addTextChangedListener(new FieldValidatorUtil(description) {
            @Override
            public void validate(EditText editText, Object text) {
                if(!isValidFieldText((String)text)){
                    editText.setError(INVALID_EMPTY_FIELD_MESSAGE);
                } else {
                    incidentDescription = (String)text;
                }
            }
        });
    }

    private void vehicleDescription(){
        EditText vehicle_description = (EditText) findViewById(R.id.vehicle_description);
        vehicle_description.addTextChangedListener(new FieldValidatorUtil(vehicle_description){
            @Override
            public void validate(EditText editText, Object text) {
                if(!isValidFieldText((String)text)){
                    editText.setError(INVALID_EMPTY_FIELD_MESSAGE);
                } else {
                    vehicleDescription = (String)text;
                }
            }
        });
    }

    private void incidentDate(){
        EditText incident_date = (EditText) findViewById(R.id.incident_date);
        incident_date.addTextChangedListener(new FieldValidatorUtil(incident_date) {
            @Override
            public void validate(EditText editText, Object date) {
                if(date == null){
                    editText.setError(INVALID_EMPTY_FIELD_MESSAGE);
                } else {
                    incidentDate = (Date)date;
                }
            }
        });
    }
}
