package com.bylawreport.flow.bylawreport.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DN on 2016-11-25.
 * Represents information for a bylaw report
 */
public class BylawReport {

    private String reporterName;
    private String reporterAddress;
    private String reporterPhone;
    private String reporterEmailAddress;

    private Date incidentDate;
    private String description;
    private ViolationType reportType;

    private List<Media> reportMedia = new ArrayList<Media>();

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

    public Date getIncidentDate() {
        return incidentDate;
    }

    public void setIncidentDate(Date incidentDate) {
        this.incidentDate = incidentDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ViolationType getReportType() {
        return reportType;
    }

    public void setReportType(ViolationType reportType) {
        this.reportType = reportType;
    }

    public List<Media> getReportMedia() {
        return reportMedia;
    }

    public void setReportMedia(List<Media> reportMedia) {
        this.reportMedia = reportMedia;
    }
}
