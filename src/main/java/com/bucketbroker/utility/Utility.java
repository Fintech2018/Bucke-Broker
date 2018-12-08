package com.bucketbroker.utility;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

public class Utility {

	
	public static String  twitterHeader1	 = "Reviewer is Oprah Winfrey, date 10/11/2016, state Texas,source is twitter" + "\r\n";
	public static String  twitterHeader2 = "Reviewer is Ma Huateng, date 10/11/2016, state New Jersey,source is twitter"+ "\r\n";
	public static String twitterDefault="Source is Twitter";
	
	
	public static String  emailOne = "Reviewer is Ana Patricia, Date 10/15/2016, state New Jersey, source is email" + "\r\n"
	+ "I was contacted for Fund Administration, associate provided detailed helpful infomration, i am very happy on their provided service, i see they are one of the biggest player in the market and been in business for years, service charges are also minimal, services offered in different channels(online, robo-advisor technology, in person).";
	
	
    public static String  emailTwo = "Reviewer is Ana Mantes, Date 10/15/2017, state New York, source is email" + "\r\n"
	+"I was contacted for Custody Service, associate provided detailed helpful infomration, i am very happy on their provided service, i see they are one of the biggest player in the market and been in business for years, service charges are also minimal, services offered in different channels(online, robo-advisor technology, in person).";
}
