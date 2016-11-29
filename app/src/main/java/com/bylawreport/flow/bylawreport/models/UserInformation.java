package com.bylawreport.flow.bylawreport.models;

import java.io.Serializable;

/**
 * Store reporter information to be passed into a Bylaw Report
 */
public class UserInformation implements Serializable {

    private String reporterName;
    private String reporterAddress;
    private String reporterPhone;
    private String reporterEmailAddress;

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public String getReporterAddress() {
        return reporterAddress;
    }

    public void setReporterAddress(String reporterAddress) {
        this.reporterAddress = reporterAddress;
    }

    public String getReporterPhone() {
        return reporterPhone;
    }

    public void setReporterPhone(String reporterPhone) {
        this.reporterPhone = reporterPhone;
    }

    public String getReporterEmailAddress() {
        return reporterEmailAddress;
    }

    public void setReporterEmailAddress(String reporterEmailAddress) {
        this.reporterEmailAddress = reporterEmailAddress;
    }
}
