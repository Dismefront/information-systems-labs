package org.dismefront.app;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Objects;

@Service
public class MinioService {

    private final MinioClient minioClient;

    public MinioService(Environment env) {
        this.minioClient = MinioClient.builder()
                .endpoint(Objects.requireNonNull(env.getProperty("MINIO_URL")))
                .credentials(Objects.requireNonNull(env.getProperty("MINIO_USERNAME")), Objects.requireNonNull(env.getProperty("MINIO_PASSWORD")))
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
            minioClient.listBuckets();
            return true;
        } catch (Exception e) {
            System.err.println("MinIO is unavailable: " + e.getMessage());
            return false;
        }
    }
}
