package com.element84.goodeatin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class CommentMakerServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException, ServletException {
		EntityManager em = EMF.get().createEntityManager();
		EntityTransaction tx = em.getTransaction();
		Restaurant r = null;
		Comment c = null;
		
		try {
			tx.begin();
			r = em.find(Restaurant.class, Long.parseLong(req.getParameter("restaurantId")));
			if (r != null) {
				c = new Comment();
				c.setCommentText(req.getParameter("comment"));
				r.getComments().add(c);
				em.persist(r);
			}
			tx.commit();
		}
		finally {
			if (tx.isActive()) {
				tx.rollback();
			}
			em.close();
		}
		
		// Try to send email to the submitter of the restaurant telling them they have 
		// a new comment.
		if ((r != null) && (r.getSubmitter() != null)) {
			emailSubmitterCommentInfo(r, c);
		}
		
		//resp.sendRedirect("/goodeatin");
	}
	
	private void emailSubmitterCommentInfo(Restaurant r, Comment c) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        StringBuilder builder = new StringBuilder();
        builder.append("Hello ");
        builder.append(r.getSubmitter().getNickname());
        builder.append(",\r\n  A new comment was added to \"");
        builder.append(r.getName());
        builder.append("\":\r\n\r\n");
        builder.append(c.getCommentText());
        String msgBody = builder.toString();

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("danbetatest@gmail.com", "Good Eatin'"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(r.getSubmitter().getEmail(), r.getSubmitter().getNickname()));
            msg.setSubject("New comment on your submission.");
            msg.setText(msgBody);
            Transport.send(msg);
    
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
