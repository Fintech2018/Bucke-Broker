package com.bucketbroker.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.bucketbroker.service.intf.BrokerService;
import com.bucketbroker.utility.CredentialUtility;
import com.bucketbroker.utility.Utility;

@Service(value="s3BucketLoaderService")
public class S3BucketLoaderService implements BrokerService {
	private static final String bucketName = "fintech-s3-uploadfb-lambda-trigger";
	//private static final String bucketName = "lambda-comprehend-sentiment";
	@Override
	public String loadSystemGeneratedFeedbackToS3(String feedbackType) {
	try {
		 AmazonS3 s3=CredentialUtility.getAWSS3Client();
		 String key = "Sys_gen_sampl"+ UUID.randomUUID()+".txt";
		 System.out.println("Uploading a new object to S3 from a file\n");
         s3.putObject(new PutObjectRequest(bucketName, key, createSampleFile(feedbackType)));
         
	 }  catch (AmazonServiceException ase) {
         System.out.println("Caught an AmazonServiceException, which means your request made it "
                 + "to Amazon S3, but was rejected with an error response for some reason.");
         System.out.println("Error Message:    " + ase.getMessage());
         System.out.println("HTTP Status Code: " + ase.getStatusCode());
         System.out.println("AWS Error Code:   " + ase.getErrorCode());
         System.out.println("Error Type:       " + ase.getErrorType());
         System.out.println("Request ID:       " + ase.getRequestId());
         return "Failure";
     } catch (AmazonClientException ace) {
         System.out.println("Caught an AmazonClientException, which means the client encountered "
                 + "a serious internal problem while trying to communicate with S3, "
                 + "such as not being able to access the network.");
         System.out.println("Error Message: " + ace.getMessage());
         return "Failure";
     } catch(Exception ex) {
    	 System.out.println("Caught an Exception, which meanssome exception occurred while performing upload");
         System.out.println("Error Message: " + ex.getMessage());
         return "Failure";
     }
		return "Success";
	}
	/**
     * Creates a temporary file with text data to demonstrate uploading a file
     * to Amazon S3
     *
     * @return A newly created temporary file with text data.
     *
     * @throws IOException
     */
    private static File createSampleFile(String feedbackType) throws IOException {
        File file = File.createTempFile("aws-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        
        if("positive".equalsIgnoreCase(feedbackType)) {
        	writer.write(Utility.positiveFB);
        }else if("negative".equalsIgnoreCase(feedbackType)) {
        	writer.write(Utility.negativeFB);
        }else if("neutral".equalsIgnoreCase(feedbackType)) {
        	writer.write(Utility.neutralFB);
        }
        writer.close();

        return file;
    } 
    
    @Override
	public String loadFeedbackToS3(File fileToLoad) {
	try {
		 AmazonS3 s3=CredentialUtility.getAWSS3Client();
		 String key = "Usr_file_sampl"+ UUID.randomUUID()+".txt";
		 System.out.println("Uploading a new object to S3 from a file\n");
         s3.putObject(new PutObjectRequest(bucketName, key, fileToLoad));
         
	 }  catch (AmazonServiceException ase) {
         System.out.println("Caught an AmazonServiceException, which means your request made it "
                 + "to Amazon S3, but was rejected with an error response for some reason.");
         System.out.println("Error Message:    " + ase.getMessage());
         System.out.println("HTTP Status Code: " + ase.getStatusCode());
         System.out.println("AWS Error Code:   " + ase.getErrorCode());
         System.out.println("Error Type:       " + ase.getErrorType());
         System.out.println("Request ID:       " + ase.getRequestId());
         return "Failure";
     } catch (AmazonClientException ace) {
         System.out.println("Caught an AmazonClientException, which means the client encountered "
                 + "a serious internal problem while trying to communicate with S3, "
                 + "such as not being able to access the network.");
         System.out.println("Error Message: " + ace.getMessage());
         return "Failure";
     } catch(Exception ex) {
    	 System.out.println("Caught an Exception, which meanssome exception occurred while performing upload");
         System.out.println("Error Message: " + ex.getMessage());
         return "Failure";
     }
		return "Success";
	}
    
    @Override
   	public String loadFeedbackToS3(String feedbackToLoad) throws IOException {
    	Writer writer=null;
   	try {
   		 AmazonS3 s3=CredentialUtility.getAWSS3Client();
   		 String key = "Usr_feed_sampl"+ UUID.randomUUID()+".txt";
   		 
   		//Create File object to load
   		 File feedbackFile=File.createTempFile("aws-java-sdk-", ".txt");
   		 feedbackFile.deleteOnExit();
	   	 writer = new OutputStreamWriter(new FileOutputStream(feedbackFile));
	     writer.write(feedbackToLoad);
	     writer.close();
   		 System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, key, feedbackFile));
            
   	 }  catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return "Failure";
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            return "Failure";
        } catch(Exception ex) {
       	 System.out.println("Caught an Exception, which meanssome exception occurred while performing upload");
            System.out.println("Error Message: " + ex.getMessage());
            return "Failure";
        }finally {
        	if(writer!=null) {
        		writer.close();
        	}
        }
   		return "Success";
   	}
    
    @Override
   	public String loadFeedToS3(String feedbackToLoad,String source) throws IOException {
    	Writer writer=null;
   	try {
   		 AmazonS3 s3=CredentialUtility.getAWSS3Client();
   		 String key = "Usr_feed_sampl"+ UUID.randomUUID()+".txt";
   		 
   		//Create File object to load
   		 File feedbackFile=File.createTempFile("aws-java-sdk-", ".txt");
   		 feedbackFile.deleteOnExit();
	   	 writer = new OutputStreamWriter(new FileOutputStream(feedbackFile));
	     writer.write(source+"\n"+feedbackToLoad);
	     writer.close();
   		 System.out.println("Uploading a new object to S3 from a file\n");
            s3.putObject(new PutObjectRequest(bucketName, key, feedbackFile));
            
   	 }  catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which means your request made it "
                    + "to Amazon S3, but was rejected with an error response for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return "Failure";
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with S3, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            return "Failure";
        } catch(Exception ex) {
       	 System.out.println("Caught an Exception, which meanssome exception occurred while performing upload");
            System.out.println("Error Message: " + ex.getMessage());
            return "Failure";
        }finally {
        	if(writer!=null) {
        		writer.close();
        	}
        }
   		return "Success";
   	}
}
