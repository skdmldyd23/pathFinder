package com.douzone.bit.pathfinder.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Uploader {

	@Autowired
	private AmazonS3 amazonS3Client;
	
	@Value("${cloud.aws.s3.bucket}")
	private String bucket;
	
	/** 
	 * 1. MultipartFile을 전달 받음.
	 * 2. S3에 전달할 수 있도록 MultiPartFile을 File로 전환.
	 *    (MultiPartFile은 S3에 전송불가)
	 * 3. 전환된 File을 S3에 public 읽기 권한으로 Put
	 * 4. 로컬로 생성된 File을 삭제.
	 * 5. 업로드된 파일의 S3 URL 주소로 반환
	 * 
	 * @Arg1 MultipartFile 업로드할 파일
	 * @Arg2 dirName S3에 생성된 디렉토리
	 * **/
	public String upload(MultipartFile multipartFile,
			String dirName) throws IOException {
		 
		File uploadFile = convert(multipartFile)
				.orElseThrow(() -> new IllegalArgumentException("MultipartFile can not convert to File."));
	
		return upload(uploadFile, dirName);
	}
	
	private String upload(File uploadFile, String dirName) {
		String fileName = dirName + "/" + uploadFile.getName();
		String uploadImageUrl = putS3(uploadFile, fileName);
		removeNewFile(uploadFile);
		
		return uploadImageUrl;
	}
	
	private String putS3(File uploadFile, String fileName) {
		amazonS3Client.putObject(new PutObjectRequest(bucket, 
				fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
		
		return amazonS3Client.getUrl(bucket, fileName).toString();
	}
	
	private void removeNewFile(File targetFile) {
		if (targetFile.delete()) {
			System.out.println("File is deleted.");
		} else {
			System.out.println("File is not deleted.");
		}
	}
	
	public void deleteFileFromS3Bucket(String fileName) {
		try {
			amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, fileName));
		} catch (AmazonServiceException e) {
			System.out.println("error [" + e.getMessage() + "] occurred while removing ["
					+ fileName + "]");
		}
	}
	
	private Optional<File> convert(MultipartFile file) throws IOException {
		File convertFile = new File(file.getOriginalFilename());
		if (convertFile.createNewFile()) {
			try (FileOutputStream fos = new FileOutputStream(convertFile)) {
				fos.write(file.getBytes());
			}
			
			return Optional.of(convertFile);
		}
		
		return Optional.empty();
	}
}
