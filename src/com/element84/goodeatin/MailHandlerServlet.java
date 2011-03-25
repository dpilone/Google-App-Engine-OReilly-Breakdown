package com.element84.goodeatin;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.logging.Logger;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class MailHandlerServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(MailHandlerServlet.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {	

	     try {
	    	 Properties props = new Properties(); 
		     Session session = Session.getDefaultInstance(props, null); 
		     MimeMessage message = new MimeMessage(session, req.getInputStream());
		     MimeMultipart mmp = (MimeMultipart)message.getContent();
		     
			 logger.info("Got email for restaurant " + message.getSubject() + " with content " + (String)mmp.getBodyPart(0).getContent());
			 
			 RequestDispatcher reqDisp = req.getRequestDispatcher("/commentMaker?restaurantId=" 
					 + Long.parseLong(message.getSubject())
					 + "&comment=" 
					 + URLEncoder.encode((String)mmp.getBodyPart(0).getContent(), "UTF-8"));
			 reqDisp.forward(req, resp);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	     
	}

}
