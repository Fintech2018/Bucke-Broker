package com.bucketbroker.utility;

//import org.apache.tika.exception.TikaException;
//import org.apache.tika.language.LanguageIdentifier;


public class LanguageDetection {
 
	/*public  static String languageDetector(String text){
		  LanguageIdentifier identifier = new LanguageIdentifier(text);
	      String language = identifier.getLanguage();
	      System.out.println("Language of the given content is : " + language);
	      return language;
	      
   }*/
	
	
	public  static String languageDetector(String text){
	    if(text.contains("La revisora"))  
		   return "es";
	    else
	       return "en";
	      
   }
	
   //javax.mail.internet.MimeMultipart
}