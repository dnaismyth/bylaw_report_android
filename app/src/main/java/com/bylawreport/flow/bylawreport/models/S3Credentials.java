package com.bylawreport.flow.bylawreport.models;

/**
 * Created by DN on 2016-12-12.
 * Model to store access credentials for S3
 */
public class S3Credentials {

    private String secretKey;
    private String accessKey;
    private String s3Bucket;

    public S3Credentials(String accessKey, String secretKey, String s3Bucket){
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.s3Bucket = s3Bucket;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getS3Bucket() {
        return s3Bucket;
    }

    public void setS3Bucket(String s3Bucket) {
        this.s3Bucket = s3Bucket;
    }
}
