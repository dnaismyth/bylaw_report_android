package com.bylawreport.flow.bylawreport.utilities;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.bylawreport.flow.bylawreport.activities.SharedPrefSingleton;
import com.bylawreport.flow.bylawreport.models.Constants;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by DN on 2016-12-22.
 */
public class S3Util extends AsyncTask<Object, Void, Object> {
    private static AmazonS3Client sS3Client;
    private static CognitoCachingCredentialsProvider sCredProvider;
    private static TransferUtility sTransferUtility;

    private static final int PUT_PORTRAIT_REQUEST_INDEX = 0;
    private static final int PUT_THUMBNAIL_REQUEST_INDEX = 1;


    public S3Util()  {}

    @Override
    protected PutS3MediaResult doInBackground(Object... params) {
        PutObjectRequest portraitRequest = (PutObjectRequest) params[PUT_PORTRAIT_REQUEST_INDEX];
        PutObjectRequest thumbnailRequest = (PutObjectRequest) params[PUT_THUMBNAIL_REQUEST_INDEX];
        ObjectMetadata portraitData = buildImageMetaData(portraitRequest);
        ObjectMetadata thumbnailData = buildImageMetaData(thumbnailRequest);

        portraitRequest.setMetadata(portraitData);
        thumbnailRequest.setMetadata(thumbnailData);
        PutObjectResult portraitResult = null;
        PutObjectResult thumbnailResult = null;
        PutS3MediaResult mediaResult = null;
        try {
            AmazonS3Client s3Client = getS3Client();
            portraitResult = s3Client.putObject(portraitRequest);
            thumbnailResult = s3Client.putObject(thumbnailRequest);
            mediaResult = new PutS3MediaResult(portraitResult, thumbnailResult);

        } catch (AmazonServiceException ase){
            Log.i("Error Message", ase.getErrorMessage());
            Log.i("HTTP Status", "[" + ase.getStatusCode() + "]");
            Log.i("AWS Error Code", "["+ ase.getErrorCode()+"]");
        }
        return mediaResult;
    }


    /**
     * Gets an instance of a S3 client which is constructed using the given
     * Context.
     *
     * @return A default S3 client.
     */
    public static AmazonS3Client getS3Client() {
        if (sS3Client == null) {
            String accessKey = SharedPrefSingleton.getInstance().getPreferenceByName(Constants.S3_ACCESS_KEY.getValue());
            String secretKey = SharedPrefSingleton.getInstance().getPreferenceByName(Constants.S3_SECRET_KEY.getValue());
            String sessionToken = SharedPrefSingleton.getInstance().getPreferenceByName(Constants.S3_SESSION_TOKEN.getValue());
            AWSCredentials s3Credentials = new BasicSessionCredentials(accessKey, secretKey, sessionToken);
            sS3Client = new AmazonS3Client(s3Credentials);
        }
        return sS3Client;
    }

    /**
     * Gets an instance of the TransferUtility which is constructed using the
     * given Context
     *
     * @param context
     * @return a TransferUtility instance
     */
    public static TransferUtility getTransferUtility(Context context) {
        if (sTransferUtility == null) {
            sTransferUtility = new TransferUtility(getS3Client(),
                    context.getApplicationContext());
        }

        return sTransferUtility;
    }

    private ObjectMetadata buildImageMetaData(PutObjectRequest por){
        ObjectMetadata data = null;
        if(por != null) {
            data = new ObjectMetadata();
            data.setContentType("image/jpeg");
            data.setContentLength(por.getFile().length());
        }
        return data;
    }

    protected void onPostExecute(Object obj){
        super.onPostExecute(obj);
    }

    private class PutS3MediaResult {
        private PutObjectResult thumbnail;
        private PutObjectResult portrait;

        private PutS3MediaResult(PutObjectResult portrait, PutObjectResult thumbnail){
            this.thumbnail = thumbnail;
            this.portrait = portrait;
        }

    }

}
