package com.bucketbroker.utility;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;

public class LanguageTranslate {
    private static final String REGION = "us-east-2";

    
  /*  public static void main(String[] args) {
    	String sourceText= "El revisor es Ana Patricia, fecha 15/10/2016 ,Estado de Nueva Jersey, la fuente es el correo electronico ,Me contactaron para la administracion del fondo, el asociado proporciono informacion util detallada, estoy muy contento con el servicio prestado, veo que es uno de los mayores jugadores en el mercado y ha estado en el negocio durante anos, cargos de servicio tambien son mini , los servicios ofrecidos en diferentes canales (en linea, tecnologia robo-consejero, en persona).";
    	translateForSomeOtherLanguageToEnglish(sourceText);
	}
	
	*/
	
    
   public static String translateForSomeOtherLanguageToEnglish(String languageWholeTextToTranslate){
    /*AmazonTranslate translate = AmazonTranslateClient.builder()
                .withRegion(REGION)
                .build(); */
    
    
    AWSCredentialsProvider awsCreds = DefaultAWSCredentialsProviderChain.getInstance();
    
    AmazonTranslate translate = AmazonTranslateClient.builder()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds.getCredentials()))
            .withRegion(REGION)
            .build();


        TranslateTextRequest request = new TranslateTextRequest()
                .withText(languageWholeTextToTranslate)
                .withSourceLanguageCode("auto")
                .withTargetLanguageCode("en");
        TranslateTextResult result  = translate.translateText(request);
        String convertedToEnglishText = result.getTranslatedText();
        String transformedText = convertedToEnglishText.replaceAll("dated", "date").replaceAll("email,", "email ,").replaceAll("State of","State")
        		.replaceAll("/2016,","/2016 ,")
        		.replaceAll("/2017,","/2017 ,")
        		.replaceAll("/2018,","/2018 ,")
        		.replaceAll("/2016,","/2016 ,")
        		.replaceAll("the fund management","Fund Administration").replaceAll("fund management","Fund Administration");
        System.out.println(transformedText);
        return transformedText;

    }
    
    
}