package org.dismefront.app;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService() {
        this.minioClient = MinioClient.builder()
                .endpoint("http://localhost:9000")
                .credentials("minioadmin", "minioadmin")
                .build();
    }

    public void uploadFile(String bucketName, String objectName, InputStream stream, long size, String contentType) {
        try {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(stream, size, -1)
                            .contentType(contentType)
                            .build()
            );
        } catch (Exception e) {
            System.err.println("MinIO upload failed: " + e.getMessage());
            throw new RuntimeException("Failed to save file to MinIO.", e);
        }
    }

    public InputStream downloadFile(String bucketName, String objectName) throws Exception {
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

    public void deleteFile(String bucketName, String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build()
            );
        } catch (Exception e) {
            System.err.println("MinIO delete failed: " + e.getMessage());
            throw new RuntimeException("Failed to delete file from MinIO.", e);
        }
    }

    public boolean isMinioAvailable() {
        try {
            minioClient.listBuckets(); // A lightweight call to verify availability
            return true;
        } catch (Exception e) {
            System.err.println("MinIO is unavailable: " + e.getMessage());
            return false;
        }
    }
}
