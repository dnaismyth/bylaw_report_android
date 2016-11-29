package com.bylawreport.flow.bylawreport.models;

import java.util.Date;

/**
 * Created by DN on 2016-11-25.
 * Media objects to be attached to bylaw reports (not required)
 */
public class Media {

    private String fileName;
    private Date createdDate;

    public Media () {}

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public Date getCreatedDate(){
        return createdDate;
    }

    public void setCreatedDate(Date createdDate){
        this.createdDate = createdDate;
    }
}
