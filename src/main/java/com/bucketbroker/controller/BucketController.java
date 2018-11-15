package com.bucketbroker.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bucketbroker.service.intf.BrokerService;

@RestController
@RequestMapping("/broker/storage/")
public class BucketController {
	private static final Logger log = LoggerFactory.getLogger(BucketController.class);
	@Autowired
	@Qualifier(value="s3BucketLoaderService")
	BrokerService brokerService;
	
	@GetMapping("/uploadFile/{feedbackType}")
	public String uploadSystemGeneratedFileToS3(@PathVariable("feedbackType") String feedbackType) {
		log.info("In system generated file upload to S3 controller..");
		return brokerService.loadSystemGeneratedFeedbackToS3(feedbackType);
	}
	
	@PostMapping("/uploadFile/feedbackFile")
	public String uploadFileToS3(@RequestParam("files") MultipartFile[] feedbackFiles) throws IOException {
		log.info("In file upload to S3 controller.."+feedbackFiles.length);
		String result=null;
		String fileName=new String();
		int count=0;
		for(MultipartFile feedbackFile:feedbackFiles) {
			byte[] bytes = feedbackFile.getBytes();
	        Path path = Paths.get(feedbackFile.getOriginalFilename());
	        Files.write(path, bytes);
			result=brokerService.loadFeedbackToS3(path.toFile());
			if(result.equalsIgnoreCase("Failure")) {
				count++;
				fileName=fileName+feedbackFile.getOriginalFilename();
			}
		}
		if(count==feedbackFiles.length) {
			return "Failure";
		}
		return fileName.equalsIgnoreCase("")?result:"Partial-Success, failed file name are:"+fileName;
	}
	
	@PostMapping("/uploadFile/feedback")
	public String uploadFileToS3(@RequestParam("feedback") String userFeedback) throws IOException {
		log.info("In feedback upload to S3 controller..");
		return brokerService.loadFeedbackToS3(userFeedback);
	}
}
