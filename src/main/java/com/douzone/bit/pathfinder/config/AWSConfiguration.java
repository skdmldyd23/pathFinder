package com.douzone.bit.pathfinder.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AWSConfiguration {

	@Value("${cloud.aws.s3.accessKey}")
	private String accessKey;
	
	@Value("${cloud.aws.s3.secretKey}")
	private String secretKey;
	
	@Value("${cloud.aws.s3.region}")
	private String region;
	
	@Bean
	public BasicAWSCredentials basicAWSCredentials() {
		
		return new BasicAWSCredentials(accessKey, secretKey);
	}
	
	@Bean
	public AmazonS3 amazonS3Client(AWSCredentials awsCredentials) {
		
		AmazonS3 amazonS3Client = AmazonS3ClientBuilder.standard()
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.withRegion(region)
				.build();
		
		return amazonS3Client;
	}
}
