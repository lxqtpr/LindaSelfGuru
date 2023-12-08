package dev.lxqtpr.lindaSelfGuru.Core.Services;

import dev.lxqtpr.lindaSelfGuru.Core.Exceptions.ImageUploadException;
import io.minio.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MinioService {

    private final MinioClient minioClient;
    public String upload(Long userId, MultipartFile image) {
        try {
            createBucket(userId);
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: "
                    + e.getMessage());
        }
        if (image.isEmpty() || image.getOriginalFilename() == null) {
            throw new ImageUploadException("Image must have name.");
        }
        String fileName = generateFileName(image);
        InputStream inputStream;
        try {
            inputStream = image.getInputStream();
        } catch (Exception e) {
            throw new ImageUploadException("Image upload failed: "
                    + e.getMessage());
        }
        saveImage(inputStream, fileName, userId);
        return generateBucketName(userId) + "/" + fileName;
    }

    @SneakyThrows
    public void deleteFile(Long userId, String fileName) {
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(generateBucketName(userId))
                        .object(fileName)
                        .build()
        );
    }

    @SneakyThrows
    private void createBucket(Long userId) {
        var bucketName = generateBucketName(userId);
        boolean found = minioClient.bucketExists(
                BucketExistsArgs
                        .builder()
                        .bucket(bucketName)
                        .build());
        if (!found) {
            minioClient.makeBucket(
                    MakeBucketArgs
                            .builder()
                            .bucket(bucketName)
                            .build());
            var jsonConfig = "{\n" +
                    "   \"Version\": \"2012-10-17\",\n" +
                    "   \"Statement\": [\n" +
                    "      {\n" +
                    "         \"Sid\": \"statement1\",\n" +
                    "         \"Effect\": \"Allow\",\n" +
                    "         \"Principal\": \"*\",\n" +
                    "         \"Action\": [\n" +
                    "            \"s3:GetObject\"\n" +
                    "         ],\n" +
                    "         \"Resource\": \"arn:aws:s3:::" + bucketName + "/*\"\n" +
                    "      }\n" +
                    "   ]\n" +
                    "}";
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs
                            .builder()
                            .config(jsonConfig)
                            .bucket(bucketName)
                            .build()
            );
        }
    }
    @SneakyThrows
    public void deleteBucket(Long userId){
        var bucketName = generateBucketName(userId);
        boolean found = minioClient.bucketExists(
                BucketExistsArgs
                        .builder()
                        .bucket(bucketName)
                        .build());
        if (found) {
            var files = minioClient.listObjects(
                    ListObjectsArgs
                            .builder()
                            .bucket(bucketName)
                            .build()
            );
            files.forEach(file -> {
                try {
                    deleteFile(userId, file.get().objectName());
                } catch (Exception e) {
                    throw new ImageUploadException(e.getMessage());
                }
            });
            minioClient.removeBucket(
                    RemoveBucketArgs
                            .builder()
                            .bucket(generateBucketName(userId))
                            .build()
            );
        }
        else{
            throw new ImageUploadException("Bucket for deleting does not exist");
        }
    }

    private String generateFileName(final MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID() + "." + extension;
    }
    private String generateBucketName(Long userId){
        return "bucket-" + userId;
    }

    private String getExtension(final MultipartFile file) {
        return file.getOriginalFilename()
                .substring(file.getOriginalFilename().lastIndexOf(".") + 1);
    }

    @SneakyThrows
    private void saveImage(final InputStream inputStream,
                           final String fileName,
                           Long userId
    ) {
        minioClient.putObject(
                PutObjectArgs.builder()
                        .stream(inputStream, inputStream.available(), -1)
                        .bucket(generateBucketName(userId))
                        .object(fileName)
                        .build());
    }

}