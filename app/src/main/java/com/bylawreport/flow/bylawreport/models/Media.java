package com.bylawreport.flow.bylawreport.models;

import java.util.Date;

/**
 * Created by DN on 2016-11-25.
 * Media objects to be attached to bylaw reports (not required)
 */
public class Media {

    private MediaData thumbnail;
    private MediaData portrait;

    public Media () {}

    public MediaData getThumbNail() {
        return thumbnail;
    }

    public void setThumbNail(MediaData thumbNail) {
        this.thumbnail = thumbNail;
    }

    public MediaData getPortrait() {
        return portrait;
    }

    public void setPortrait(MediaData portrait) {
        this.portrait = portrait;
    }
}
