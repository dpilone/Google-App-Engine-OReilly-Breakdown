package com.element84.goodeatin;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;
import com.google.appengine.api.labs.taskqueue.TaskOptions.Method;

@SuppressWarnings("serial")
public class AsynchronousCommentMakerServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(AsynchronousCommentMakerServlet.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException, ServletException {
		logger.info("Queuing comment addition for restaurant id: " + req.getParameter("restaurantId"));
		
		Queue queue = QueueFactory.getDefaultQueue();
		queue.add(TaskOptions.Builder.url("/commentMaker")
				.param("restaurantId", req.getParameter("restaurantId"))
				.param("comment", req.getParameter("comment"))
				.method(Method.POST)
				.countdownMillis(20000)
				);
		
		resp.sendRedirect("/goodeatin");
	}
}
