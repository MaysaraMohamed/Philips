package com.philips.backend.logic;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Controller;



@Controller
public class MailManagement {

	   public boolean sendmail(String messge, String subject, String mailTo) throws AddressException, MessagingException, IOException {
		   
		   // Mail configurations should be reviewed and updated. 
		   Properties props = new Properties();
		   props.put("mail.smtp.auth", "true");
		   props.put("mail.smtp.starttls.enable", "true");
		   props.put("mail.smtp.host", "bbiconsultancy-com0i.mail.protection.outlook.com");
		   props.put("mail.smtp.port", "25");
				   
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication("maysara.mohamed@bbi-consultancy.com", "bBi123$%^");
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress("maysara.mohamed@bbi-consultancy.com", false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
		   msg.setSubject(subject);
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   String contentMessage = "<br><br>"+messge+"<br><br>"; 
		   messageBodyPart.setContent(contentMessage, "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);

		   // the below part used for attachment 
//		   MimeBodyPart attachPart = new MimeBodyPart();
//		   attachPart.attachFile("/var/tmp/image19.png");
//		   multipart.addBodyPart(attachPart);
		   msg.setContent(multipart);
		   Transport.send(msg);  
		   return true; 
		}
	   
	   public String generatePassword()
	   {
		   String part1 = RandomStringUtils.random(2, false, true);
		   String part2 = RandomStringUtils.randomAlphabetic(2);
		   String part3 = RandomStringUtils.random(2, false, true);
		   String part4 = RandomStringUtils.randomAlphabetic(2);
		   return part1+part2+part3+part4; 
	   }
}




   
   
