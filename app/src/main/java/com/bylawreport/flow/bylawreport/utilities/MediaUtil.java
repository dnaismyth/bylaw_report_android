package com.bylawreport.flow.bylawreport.utilities;

import android.graphics.Bitmap;
import android.util.Log;

import com.bylawreport.flow.bylawreport.models.MediaData;
import com.bylawreport.flow.bylawreport.models.MediaType;

/**
 * Created by DN on 2016-12-24.
 */
public class MediaUtil {

    /**
     * Build Portrait image for media
     * @param imageMap
     * @param s3Key
     * @return
     */
    public MediaData buildPortrait(Bitmap imageMap, String s3Key){
        MediaData data = null;
        if(imageMap != null){
            data = new MediaData();
            data.setHeight(imageMap.getHeight());
            data.setWidth(imageMap.getWidth());
            data.setS3Key(s3Key);
            data.setMediaType(MediaType.PORTRAIT);
        }
        return data;
    }

    /**
     * Build Thumbnail image for Media
     * @param imageMap
     * @param s3Key
     * @return
     */
    public MediaData buildThumbnail(Bitmap imageMap, String s3Key){
        MediaData data = null;
        if(imageMap != null){
            data = new MediaData();
            data.setHeight(imageMap.getHeight());
            data.setWidth(imageMap.getWidth());
            data.setS3Key(s3Key);
            data.setMediaType(MediaType.THUMBNAIL);
        }
        return data;
    }

    public static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            Log.d("Bitmap resize", "Beginning resize...");
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > 1) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            Log.d("Bitmap resize", "Finished.");
            return image;
        } else {
            return image;
        }
    }
}
