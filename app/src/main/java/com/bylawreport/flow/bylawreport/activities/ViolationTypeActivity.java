package com.bylawreport.flow.bylawreport.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.Toast;

import com.bylawreport.flow.bylawreport.R;
import com.bylawreport.flow.bylawreport.models.Constants;
import com.bylawreport.flow.bylawreport.models.ViolationType;

/**
 * Created by DN on 2016-11-20.
 */
public class ViolationTypeActivity extends AppCompatActivity {

    private RadioGroup violationTypeGroup;
    private Button continueBtn;
    private ViolationType type = null;
    private static final String INVALID_MESSAGE = "Please select a violation type.";

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_type);

        // Set up listener for violation type entry
        addListenerOnViolationType();
    }

    /**
     * Create listener for radio group
     */
    public void addListenerOnViolationType() {
        violationTypeGroup = (RadioGroup) findViewById(R.id.radioGroup_violation);
        continueBtn = (Button)findViewById(R.id.beginUserInfoBtn);

       violationTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(RadioGroup group, int checkedId) {
               AppCompatRadioButton rb = (AppCompatRadioButton) group.findViewById(checkedId);
               if(null!=rb && checkedId > -1){
                   String buttonId = getButtonStringId(rb, checkedId);
                   switch(buttonId){
                       case "vehicle_violation":
                           type = ViolationType.TRAFFIC;
                           break;
                       case "property_violation":
                           type = ViolationType.PROPERTY;
                           break;
                       case "other_violation":
                           type = ViolationType.OTHER;
                           break;
                   }
               }
           }
       } );
    }

    /**
     * Return the string id of the button chosen
     * @param rb
     * @param checkedId
     * @return
     */
    private String getButtonStringId(RadioButton rb, int checkedId){
        return rb.getResources().getResourceEntryName(checkedId);
    }

    /**
     * Switch to user information view
     * @param view
     */
    public void sendViolationTypeChoice(View view){

        if(formIsValid()) {
            Intent i = new Intent(getApplicationContext(), UserInformationActivity.class);
            i.putExtra(Constants.VIOLATION_TYPE.getValue(), type);
            startActivity(i);
        } else {
            Toast.makeText(getBaseContext(),INVALID_MESSAGE, Toast.LENGTH_SHORT).show();
        }

    }

    // Check that the form is valid and a type has been selected
    private boolean formIsValid(){
        boolean approved = false;
        if(type != null){
            approved = true;
        }
        return approved;
    }
}
