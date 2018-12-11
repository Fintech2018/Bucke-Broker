package com.bucketbroker.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

import com.bucketbroker.email.EmailManager;
import com.bucketbroker.service.intf.BrokerService;
import com.bucketbroker.twitter.TweetManager;
import com.bucketbroker.utility.Utility;

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
		return brokerService.loadFeedbackToS3(Utility.headerFB+userFeedback);
	}
	
	@GetMapping("/uploadFile/sync_twitter")
	public String syncTwitter() {
		log.info("In sync twitter controller....");
		List<String> tweets=TweetManager.getTweets("BBH PRIVATE BANKING");
		if(tweets!=null && tweets.isEmpty()) {
			return "No New Tweets Found For BBH";
		}
		int t=1;
		String twitterHName;
		  for(String feedback:tweets) {
			  if(t==1) {
				  twitterHName=Utility.twitterHeader1;
			  }else if(t==2) {
				  twitterHName=Utility.twitterHeader2;
			  }else
			  {
				  twitterHName= Utility.twitterDefault;
			  }
			 
			 try {
				  brokerService.loadFeedToS3(feedback,twitterHName);
			  System.out.println("Header Name feedback---->"+twitterHName);
			  System.out.println("Twitter feedback---->"+feedback);
			
			} catch (IOException e) {
				log.error("Exception while processing tweets for upload",e.getMessage());
			}
			  t++;
		  }
		  return "Success";
	}
	
	@GetMapping("/uploadFile/sync_email")
	public String syncEmail() {
		log.info("In sync email controller....");
		List<String> emails=EmailManager.syncEmail();
		if(emails!=null && emails.isEmpty()) {
			return "No New Email Found For BBH";
		}
		  for(String feedback:emails) {
			  
			 try {
				 System.out.println("EMAIL feedback-->"+feedback);
			  brokerService.loadFeedToS3(feedback,"");
				  System.out.println(feedback);
			} catch (IOException e) {
				log.error("Exception while processing tweets for upload",e.getMessage());
			}
		  }
		  return "Success";
	}
}
