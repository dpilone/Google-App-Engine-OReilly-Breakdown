package com.element84.goodeatin;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class DefaultTaskQueueServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(DefaultTaskQueueServlet.class.getName());
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException, ServletException {
		logger.info("DefaultTaskQueue servlet called!");
	}
}
