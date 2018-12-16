package com.bucketbroker.email;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;

import com.bucketbroker.utility.LanguageTranslate;
import com.bucketbroker.utility.Utility;

public class EmailManager {
	
	static String host = "pop.gmail.com";// change accordingly
	static String mailStoreType = "pop3";
	static String username = "fintechfeedback@gmail.com";// change accordingly
	static String password = "fintech2018";// change accordingly

	public static List<String> syncEmail() {
		
		return check(host, mailStoreType, username, password);
	}

	// function to make connection and get mails from server known as "Pop" server
	public static List<String> check(String host, String storeType, String user, String password) {
		List<String> messageList=new ArrayList<String>();
		try {

			// create properties field
			Properties properties = new Properties();

			properties.put("mail.pop3.host", host);
			properties.put("mail.pop3.port", "995");
			properties.put("mail.pop3.starttls.enable", "true");
			//Session emailSession = Session.getDefaultInstance(properties);
			Session emailSession = Session.getInstance(properties, new javax.mail.Authenticator() {
			    protected PasswordAuthentication getPasswordAuthentication() {
			        return new PasswordAuthentication(user, password);
			    }
			});

			// create the POP3 store object and connect with the pop server
			Store store = emailSession.getStore("pop3s");

			store.connect(host, user, password);

			// create the folder object and open it
			Folder emailFolder = store.getFolder("Inbox");

			emailFolder.open(Folder.READ_ONLY);

			// retrieve the messages from the folder in an array and print it
			Message[] messages = emailFolder.getMessages();
			System.out.println("messages.length---" + messages.length);

			for (int i = 0, n = messages.length; i < n; i++) {
				Message message = messages[i];

				Object obj = message.getContent();
				Multipart mp = (Multipart) obj;
				BodyPart bp = mp.getBodyPart(0);

				System.out.println("---------------------------------");
				System.out.println("Email Number " + (i + 1));
				/*System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				System.out.println("To: " + message.getAllRecipients().toString());
				System.out.println("Received Date:" + message.getReceivedDate());*/
				System.out.println("Text: " + bp.getContent().toString());
				
				/*if(message.getSubject().contains("BBH")) {
					messageList.add(bp.getContent().toString());
				}*/
				
				if(bp.getContent().toString().contains("La revisora")){
					messageList.add(LanguageTranslate.translateForSomeOtherLanguageToEnglish(bp.getContent().toString(), "es"));
				}
				else{
					messageList.add(bp.getContent().toString());
				}
				
			}

			// close the store and folder objects
			emailFolder.close(false);
			store.close();

		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("EMAIl EXCEPTION BLOCK");
			messageList.add(Utility.emailOne);
			messageList.add(Utility.emailTwo);
		}
		return messageList;
	}
}
