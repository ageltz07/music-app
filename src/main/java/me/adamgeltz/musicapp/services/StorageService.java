package me.adamgeltz.musicapp.services;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StorageService {

    // Create amazonS3 object to interact with Digital Ocean space
    private final AmazonS3 space;

    public StorageService() {

        // Use Credentials from digital ocean
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(
                new BasicAWSCredentials("enterAccessKey", "enterSecretKey")
        );

        space = AmazonS3ClientBuilder
                .standard()
                .withCredentials(awsCredentialsProvider)
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("nyc3.digitaloceanspaces.com", "nyc3")
                )
                .build();
    }

    public List<String> getSongFileNames() {
        ListObjectsV2Result result = space.listObjectsV2("enterNameHere");
        List<S3ObjectSummary> objects = result.getObjectSummaries();

        // Streaming through each object in list of S3Object summaries
        // and were changing each into a key with get key, which represents
        // the file name of these objects. We are creating a new list out of these
        // names and then returning that list.
        return objects.stream()
                .map(S3ObjectSummary::getKey).collect(Collectors.toList());
    }

    public void uploadSong(MultipartFile file) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        //enter bucketName
        space.putObject(new PutObjectRequest("enterNameHere", file.getOriginalFilename(), file.getInputStream(), objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
