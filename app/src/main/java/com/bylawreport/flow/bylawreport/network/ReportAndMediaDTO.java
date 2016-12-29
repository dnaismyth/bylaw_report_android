package com.bylawreport.flow.bylawreport.network;

import com.bylawreport.flow.bylawreport.models.BylawReport;
import com.bylawreport.flow.bylawreport.models.Media;

/**
 * Created by DN on 2016-12-24.
 */
public class ReportAndMediaDTO {
    private BylawReport report;
    private Media reportMedia;

    public ReportAndMediaDTO(BylawReport report, Media reportMedia){
        this.report = report;
        this.reportMedia = reportMedia;
    }

    public BylawReport getReport() {
        return report;
    }

    public void setReport(BylawReport report) {
        this.report = report;
    }

    public Media getReportMedia() {
        return reportMedia;
    }

    public void setReportMedia(Media reportMedia) {
        this.reportMedia = reportMedia;
    }
}
