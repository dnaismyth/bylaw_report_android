package com.bylawreport.flow.bylawreport.utilities;

import android.content.Context;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bylawreport.flow.bylawreport.models.S3Credentials;

import java.net.URI;

/**
 * Created by DN on 2016-12-22.
 */
public class S3Util {
    private static AmazonS3Client sS3Client;
    private static CognitoCachingCredentialsProvider sCredProvider;
    private static TransferUtility sTransferUtility;

    /**
     * Gets an instance of a S3 client which is constructed using the given
     * Context.
     *
     * @param credentials
     * @return A default S3 client.
     */
    public static AmazonS3Client getS3Client(S3Credentials credentials) {
        if (sS3Client == null) {
            AWSCredentials s3Credentials = new BasicAWSCredentials(credentials.getAccessKey(), credentials.getSecretKey());
            sS3Client = new AmazonS3Client(s3Credentials);
        }
        return sS3Client;
    }

    /**
     * Gets an instance of the TransferUtility which is constructed using the
     * given Context
     *
     * @param context
     * @param credentials
     * @return a TransferUtility instance
     */
    public static TransferUtility getTransferUtility(S3Credentials credentials, Context context) {
        if (sTransferUtility == null) {
            sTransferUtility = new TransferUtility(getS3Client(credentials),
                    context.getApplicationContext());
        }

        return sTransferUtility;
    }

    /**
     *
     * @param credentials
     * @param imageName
     * @param file
     */
    public static void putObject(S3Credentials credentials, String imageName, URI file){
        PutObjectRequest por = new PutObjectRequest( credentials.getS3Bucket(), imageName, new java.io.File(file) );
        sS3Client.putObject( por );
    }
}
