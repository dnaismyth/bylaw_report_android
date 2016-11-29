package com.bylawreport.flow.bylawreport.utilities;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Validate user input for form fields
 * Created by DN on 2016-11-26.
 */
public abstract class FieldValidatorUtil implements TextWatcher {

    private final EditText editText;

    public FieldValidatorUtil(EditText editText){
        this.editText = editText;
    }

    public abstract void validate(EditText editText, Object input);

    @Override
    final public void afterTextChanged(Editable s) {
        String text = editText.getText().toString();
        validate(editText, text);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {}

    public static boolean isValidFieldText(String fieldText){
        if(fieldText == null || fieldText.equals("") || fieldText.length() < 1){
            return false;
        }
        return true;
    }
}
