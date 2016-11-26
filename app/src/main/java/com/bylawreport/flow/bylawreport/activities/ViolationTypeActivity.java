package com.bylawreport.flow.bylawreport.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Button;
import android.widget.Toast;

import com.bylawreport.flow.bylawreport.R;

/**
 * Created by DN on 2016-11-20.
 */
public class ViolationTypeActivity extends AppCompatActivity {

    private RadioGroup violationTypeGroup;
    private RadioButton violationBtn;
    private Button continueBtn;

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
               RadioButton rb = (RadioButton) group.findViewById(checkedId);
               if(null!=rb && checkedId > -1){
                   Toast.makeText(ViolationTypeActivity.this, rb.getText(), Toast.LENGTH_SHORT).show(); // temporary, store user input
               }
           }
       } );



    }

    /**
     * Switch to user information view
     * @param view
     */
    public void sendViolationTypeChoice(View view){
        Intent i = new Intent(getApplicationContext(), UserInformationActivity.class);
        startActivity(i);
    }

}
