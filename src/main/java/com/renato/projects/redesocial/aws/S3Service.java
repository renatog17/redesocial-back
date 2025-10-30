package com.renato.projects.redesocial.aws;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.model.S3Object;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${app.s3.bucket-name}")
    private String bucketName;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @PostConstruct
    public void init() {
        checkBucketAccess();
        listFiles();
    }
    public void uploadFile(String key, File file) {
        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(getBucketName())
                        .key(key)
                        .build(),
                RequestBody.fromFile(file)
        );
        System.out.println("File uploaded: " + key);
    }

    public void downloadFile(String key, String localPath) {
        s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket(getBucketName())
                        .key(key)
                        .build(),
                Paths.get(localPath)
        );
        System.out.println("File downloaded to: " + localPath);
    }

    public boolean checkBucketAccess() {
        try {
            s3Client.headBucket(b -> b.bucket(getBucketName()));
            System.out.println("Access to bucket confirmed: " + getBucketName());
            return true;
        } catch (Exception e) {
            System.err.println("Failed to access bucket: " + e.getMessage());
            return false;
        }
    }
    
    public List<String> listFiles() {
        List<String> files = new ArrayList<>();
        try {
            ListObjectsV2Request request = ListObjectsV2Request.builder()
                    .bucket(getBucketName())
                    .build();

            ListObjectsV2Response result = s3Client.listObjectsV2(request);

            for (S3Object obj : result.contents()) {
                files.add(obj.key());
                System.out.println("File found: " + obj.key() + " (" + obj.size() + " bytes)");
            }

            if (files.isEmpty()) {
                System.out.println("No files found in bucket: " + getBucketName());
            }

        } catch (S3Exception e) {
            System.err.println("Error listing files: " + e.awsErrorDetails().errorMessage());
        }
        return files;
    }

	public String getBucketName() {
		return bucketName;
	}
}
