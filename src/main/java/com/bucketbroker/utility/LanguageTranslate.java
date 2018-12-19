package com.bucketbroker.utility;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

public class LanguageTranslate {
    private static final String REGION = "us-east-2";

    /*public static void main(String[] args) {
    	String sourceText= "Reviewer is Jose Robert,        Date        10/15/2017 ,state        New York, source is email ,I was contacted for Custody Service, associate provided detailed helpful information, i am very happy on their provided service, i see they are one of the biggest player in the market and been in business for years, service charges are also minimal, services offered in different channels(online, robo-advisor technology, in person).";
    	translateForSomeOtherLanguageToEnglish(sourceText);
	}*/
    
   public static String translateForSomeOtherLanguageToEnglish(String languageWholeTextToTranslate){
    	
	   BasicAWSCredentials credentialsProvider = new BasicAWSCredentials("AKIAIIIQFUE6PMCA7PIA", "rSifgZt5DbBtpK5SOmPIM13R6YtF7wRXmrU8+OwU");
	   
	   AmazonTranslate translate = AmazonTranslateClient.builder().withCredentials(new AWSStaticCredentialsProvider(credentialsProvider))
                .withRegion(REGION)
                .build();

        TranslateTextRequest request = new TranslateTextRequest()
                .withText(languageWholeTextToTranslate)
                .withSourceLanguageCode("auto")
                .withTargetLanguageCode("en");
        TranslateTextResult result  = translate.translateText(request);
        String convertedToEnglishText = result.getTranslatedText();
        String transformedText = convertedToEnglishText.replaceAll("dated", "date").replaceAll("email,", "email ,");
        System.out.println(transformedText);
        return transformedText;

    }
    
    
}