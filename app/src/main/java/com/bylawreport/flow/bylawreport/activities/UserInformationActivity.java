package com.bylawreport.flow.bylawreport.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bylawreport.flow.bylawreport.R;
import com.bylawreport.flow.bylawreport.models.UserInformation;
import com.bylawreport.flow.bylawreport.utilities.FieldValidatorUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DN on 2016-11-20.
 */
public class UserInformationActivity extends AppCompatActivity {

    private UserInformation currentUserInfo = null;
    private static final String INVALID_EMPTY_FIELD_MESSAGE = "This field cannot be left blank.";
    private static final String INVALID_FORM_MESSAGE = "Please fill out all required fields.";
    String email = null;
    String phone = null;
    String address = null;
    String firstName = null;
    String lastName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        addEditTextListeners();
    }

    private void reporterEmail(){
        EditText emailField = (EditText) findViewById(R.id.email);
        emailField.addTextChangedListener(new FieldValidatorUtil(emailField) {
            @Override
            public void validate(EditText editText, Object text) {
                if(!isValidFieldText((String)text)){
                    editText.setError(INVALID_EMPTY_FIELD_MESSAGE);
                } else {
                    email = (String)text;
                }
            }
        });
    }

    private void reporterFirstName(){
        EditText firstNameField = (EditText) findViewById(R.id.first_name);
        firstNameField.addTextChangedListener(new FieldValidatorUtil(firstNameField) {
            @Override
            public void validate(EditText editText, Object text) {
                if(!isValidFieldText((String)text)){
                    editText.setError(INVALID_EMPTY_FIELD_MESSAGE);
                } else {
                    firstName = (String)text;
                }
            }
        });
    }

    private void reporterLastName(){
        EditText lastNameField = (EditText) findViewById(R.id.last_name);
        lastNameField.addTextChangedListener(new FieldValidatorUtil(lastNameField) {
            @Override
            public void validate(EditText editText, Object text) {
                if(!isValidFieldText((String)text)){
                    editText.setError(INVALID_EMPTY_FIELD_MESSAGE);
                } else {
                    lastName = (String)text;
                }
            }
        });

    }

    private void reporterAddress(){
        EditText addressField = (EditText) findViewById(R.id.address);
        addressField.addTextChangedListener(new FieldValidatorUtil(addressField) {
            @Override
            public void validate(EditText editText, Object text) {
                if(!isValidFieldText((String)text)){
                    editText.setError(INVALID_EMPTY_FIELD_MESSAGE);
                } else {
                    address = (String)text;
                }
            }
        });
    }

    private void reporterPhone(){
        EditText phoneField = (EditText) findViewById(R.id.phone);
        phoneField.addTextChangedListener(new FieldValidatorUtil(phoneField) {
            @Override
            public void validate(EditText editText, Object text) {
                if(!isValidFieldText((String)text)){
                    editText.setError(INVALID_EMPTY_FIELD_MESSAGE);
                } else {
                    phone = (String)text;
                }
            }
        });
    }

    // Add user information into a list for easier looping
    private List<String> addUserInfoValuesToList(){
        List<String> userInfo = new ArrayList<String>();
        userInfo.add(email);
        userInfo.add(phone);
        userInfo.add(address);
        userInfo.add(firstName);
        userInfo.add(lastName);
        return userInfo;
    }

    private void addEditTextListeners(){
        reporterFirstName();
        reporterLastName();
        reporterEmail();
        reporterAddress();
        reporterPhone();
    }

    private boolean formIsValid(){
        List<String> allFields = addUserInfoValuesToList();
        for(String s : allFields){
            if(!FieldValidatorUtil.isValidFieldText(s)){
                return false;
            }
        }
        return true;
    }

    /**
     * Send user information and begin report
     * @param v
     */
    public void sendUserInformation(View v){
        if(formIsValid()) {
            currentUserInfo = buildUserInformationWithFormInput();   // build user information from form
            Intent i = new Intent(getApplicationContext(), ReportInformationActivity.class);
            i.putExtra("currentUserInfo", currentUserInfo);
            startActivity(i);
        } else {
            Toast.makeText(getBaseContext(),INVALID_FORM_MESSAGE, Toast.LENGTH_SHORT).show();
        }
    }

    private UserInformation buildUserInformationWithFormInput(){
        UserInformation reporter = new UserInformation();
        reporter.setReporterAddress(address);
        reporter.setReporterEmailAddress(email);
        reporter.setReporterName(firstName.concat(" " + lastName));
        reporter.setReporterPhone(phone);
        return reporter;
    }


}
