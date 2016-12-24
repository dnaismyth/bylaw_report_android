package com.bylawreport.flow.bylawreport.models;

/**
 * Created by DN on 2016-12-20.
 * Store constants to be used as references to REST Response data
 */
public enum Constants {
    ACCESS_TOKEN("id_token"),    // guest user access token
    RESPONSE_DATA("data"),          // data envelope
    REPORTER_INFO("currentUserInfo"), // basic reporter (user) information
    VIOLATION_TYPE("violationType"); // type of violation the user is reporting (property, vehicle etc..)


    private String constant;

    Constants(String constant) {
        this.constant = constant;
    }

    public String getValue() { return constant; }
}
