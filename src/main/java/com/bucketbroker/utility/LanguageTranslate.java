package com.bucketbroker.utility;

import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

public class LanguageTranslate {
    private static final String REGION = "us-east-2";

   public static String translateForSomeOtherLanguageToEnglish(String languageWholeTextToTranslate
    		,String originalLanguage){
    	
    	AmazonTranslate translate = AmazonTranslateClient.builder()
                .withRegion(REGION)
                .build();

        TranslateTextRequest request = new TranslateTextRequest()
                .withText(languageWholeTextToTranslate)
                .withSourceLanguageCode(originalLanguage)
                .withTargetLanguageCode("en");
        TranslateTextResult result  = translate.translateText(request);
        String convertedToEnglishText = result.getTranslatedText();
        System.out.println(convertedToEnglishText);
        return convertedToEnglishText;

    }
    
    
}