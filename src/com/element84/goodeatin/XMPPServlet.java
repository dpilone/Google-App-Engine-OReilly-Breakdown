package com.element84.goodeatin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

@SuppressWarnings("serial")
public class XMPPServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(XMPPServlet.class.getName());
	
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException  {
		XMPPService xmpp = XMPPServiceFactory.getXMPPService();
		Message message = xmpp.parseMessage(req);
	
		logger.info("Received IM from: " + message.getFromJid().getId() + " that says " + message.getBody());
		
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add();
				
		MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
		ArrayList<Restaurant> restList = (ArrayList<Restaurant>)memcache.get("top10restaurants");
		
		String responseBody = null;
		if (restList != null) {
			StringBuilder builder = new StringBuilder();
			builder.append("Current top 3 restaurants: \r\n");
			for (int i = 0; i < 3; i ++) {
				builder.append(restList.get(i).getName());
				builder.append("\r\n");
			}
			responseBody = builder.toString();
		}
		else {
			responseBody = "Sorry, don't know yet.";
		}
		
		Message respMessage = new MessageBuilder().withFromJid(message.getRecipientJids()[0])
								.withRecipientJids(message.getFromJid())
								.withBody(responseBody)
								.build();
		SendResponse success = xmpp.sendMessage(respMessage);
		if (success.getStatusMap().get(message.getFromJid()) != SendResponse.Status.SUCCESS) {
			logger.warning("Failed to send message!");
		}	
	}
}
