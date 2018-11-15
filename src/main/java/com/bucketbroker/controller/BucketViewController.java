package com.bucketbroker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BucketViewController {
	private static final Logger log = LoggerFactory.getLogger(BucketViewController.class);
	
	@GetMapping("/")
	public String uploadFileToS3() {
		log.info("In upload to S3 controller..");
		return "upload";
	}
	
}
