package org.dismefront.app.health;

import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MinioHealthIndicator implements HealthIndicator {

  @Value("${MINIO_URL}")
  private String minioUrl;

  @Value("${MINIO_USERNAME}")
  private String minioUsername;

  @Value("${MINIO_PASSWORD}")
  private String minioPassword;

  @Override
  public Health health() {
    try {
      MinioClient minioClient =
          MinioClient.builder()
              .endpoint(minioUrl)
              .credentials(minioUsername, minioPassword)
              .build();

      // Try to ping MinIO by checking if we can build the client
      minioClient.listBuckets();

      return Health.up().withDetail("service", "MinIO").withDetail("url", minioUrl).build();

    } catch (Exception e) {
      log.error("MinIO health check failed", e);
      return Health.down()
          .withDetail("service", "MinIO")
          .withDetail("url", minioUrl)
          .withException(e)
          .build();
    }
  }
}
