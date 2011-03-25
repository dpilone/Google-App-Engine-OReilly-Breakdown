package com.element84.goodeatin;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.labs.taskqueue.Queue;
import com.google.appengine.api.labs.taskqueue.QueueFactory;
import com.google.appengine.api.labs.taskqueue.TaskOptions;

@SuppressWarnings("serial")
public class RestaurantInfoRefresherServlet extends HttpServlet {
	private static Logger logger = Logger.getLogger(DefaultTaskQueueServlet.class.getName());
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException, ServletException {
		logger.info("Restaurant refresher starting...");
		
		// Do a key only query
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			// Initial query
			Query query = em.createQuery("SELECT id from Restaurant ORDER BY id ");
			query.setMaxResults(10);
			List<Key> resultList = query.getResultList();
			
			while(resultList.size() > 0) {
				// Queue a task to handle these and fetch the next batch.
				Queue queue = QueueFactory.getDefaultQueue();
				queue.add(TaskOptions.Builder.url("/restaurantInfoRefresher")
						.param("startKey", Long.toString(resultList.get(0).getId()))
						.param("endKey", Long.toString(resultList.get(resultList.size()-1).getId())));
			
				// Query for the next batch of 10
				query = em.createQuery("SELECT id from Restaurant WHERE id > :lastEnd ORDER BY id");
				query.setParameter("lastEnd", resultList.get(resultList.size()-1).getId());
				query.setMaxResults(10);
				resultList = query.getResultList();
			}
		}
		finally {
			em.close();
		}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException, ServletException {
		logger.info("Refreshing information for resturants starting at: " + req.getParameter("startKey")
				+ " and ending at " + req.getParameter("endKey"));
		// Do stuff..
	}
}