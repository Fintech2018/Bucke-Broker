package com.bucketbroker.utility;

import com.amazonaws.services.translate.AmazonTranslate;
import com.amazonaws.services.translate.AmazonTranslateClient;
import com.amazonaws.services.translate.model.TranslateTextRequest;
import com.amazonaws.services.translate.model.TranslateTextResult;

public class LanguageTranslate {
    private static final String REGION = "us-east-2";

    public static void main( String[] args ) {
        LanguageTranslate translate = new LanguageTranslate();
    	translate.translateForSomeOtherLanguageToEnglish("La revisora ​​es Ana Patricia, fecha 19/10/2016 ,estado New Jersey, fuente es correo electrónico ,Me contactaron para la Administración del Fondo, el asociado proporcionó información útil detallada, estoy muy contento con el servicio prestado, veo que es uno de los jugadores más grandes en el mercado y ha estado en el negocio durante años, los cargos por servicio también son mínimos, los servicios ofrecidos en Diferentes canales (online, tecnología robo-asesor, en persona).", "es");
    }
    
    
    public String translateForSomeOtherLanguageToEnglish(String languageWholeTextToTranslate
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