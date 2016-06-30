package com.keyword.sample;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.keyword.sample.Constants;
import com.keyword.sample.KeyWords;

/**
 * SendMail class. Sends the execution report in email to the intended recipients.
 *  
 * @author venkatachalam.c
 */
public class SendMail {
	
	public void sendEmail() throws IOException{
		
 		final String username = "testautomationsrijan@gmail.com";
 		final String password = "srijan@123";
 		String buildNo = Constants.BUILD_NUMBER;
 		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss");
 		final String current_DateTime = formatter.format(new Date().getTime());
 		String [] to = {"sweta.s@srijan.in"};
 		
 		Properties props = new Properties();
 		props.put("mail.smtp.auth", "true");
 		props.put("mail.smtp.starttls.enable", "true");
 		props.put("mail.smtp.host", "smtp.gmail.com");
 		props.put("mail.smtp.port", "587");

 		Session session = Session.getInstance(props,
 				new javax.mail.Authenticator() {
 			protected PasswordAuthentication getPasswordAuthentication() {
 				return new PasswordAuthentication(username, password);
 			}
 		});

 		try {

 			Message message = new MimeMessage(session);
 			message.setFrom(new InternetAddress(username));
 			for(int i=0;i< to.length;i++)
 			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to[i]));
 			
 			Date date = new Date(); 			
 			message.setSubject("Test Execution Report - " + date); 			
 			MimeBodyPart messageBodyPart = new MimeBodyPart();
 			//MimeBodyPart messageBodyPart1 = new MimeBodyPart();
 			messageBodyPart.setText("\n\nHi  Team, \n\n\n   Please find the attached Test Execution Report.\n\n"
 								+ "\n\nQA Environment: Staging\n" + "\n Build Number: " + buildNo
 								+ "\n\n\n\nThanks, \nAutomation Team\nSrijan Technologies");
 			Multipart multipart = new MimeMultipart();
 			multipart.addBodyPart(messageBodyPart);
 			 			
 			messageBodyPart = new MimeBodyPart();
 			//messageBodyPart1 = new MimeBodyPart();
 			
 			final File outputFile1 = KeyWords.lastFileModified(Constants.HTML_REPORTS_DIR);
 			//final File outputFile2 = KeyWords.lastFileModified(Constants.SCREENSHOT_DIR);
 			String fileName1 = "App TestExecutionReport-"+current_DateTime+".html";
 			//String fileName2 = "CIPApp TestExecutionReport-"+current_DateTime+".png";
 			DataSource source1 = new FileDataSource(outputFile1);
 			//DataSource source2 = new FileDataSource(outputFile2);
 			messageBodyPart.setDataHandler(new DataHandler(source1));
 			//messageBodyPart1.setDataHandler(new DataHandler(source2));
 			messageBodyPart.setFileName(fileName1);
 			multipart.addBodyPart(messageBodyPart);
 			//multipart.addBodyPart(messageBodyPart1);
 			message.setContent(multipart);
 			
 			System.out.println("Sending Mail");
 			Transport.send(message);
 			System.out.println("Email report sent successfully to the recipients!");

 		} catch (MessagingException e) {
 			throw new RuntimeException(e);
 		}
 	}

}

