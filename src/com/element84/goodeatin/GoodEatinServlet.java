package com.element84.goodeatin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

@SuppressWarnings("serial")
public class GoodEatinServlet extends HttpServlet {
	
	private static Logger logger = Logger.getLogger(GoodEatinServlet.class.getName());
	
	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException  {
		List<Restaurant> restList = null;
	
		logger.info("GoodEatinServlet hit...");

		EntityManager em = EMF.get().createEntityManager();
		
		try {
			MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
			restList = (List<Restaurant>)memcache.get("top10restaurants");
			
			if (restList != null) {
				logger.info("CACHE HIT: Found restaurants.");
			}
			else {
				logger.info("CACHE MISS: Fetching from DB");
				Query query = em.createQuery("SELECT r FROM Restaurant r ORDER BY dateAdded DESC");
				query.setMaxResults(10);
				restList = query.getResultList();
				ArrayList<Restaurant> serializableList = new ArrayList<Restaurant>();
				serializableList.addAll(restList);
				memcache.put("top10restaurants", serializableList, Expiration.byDeltaSeconds(30));
			}
			
			req.setAttribute("restaurants", restList);
			
			RequestDispatcher view = req.getRequestDispatcher("listView.jsp");
			view.forward(req, resp);
		}
		finally {
			em.close();
		}
	}
}
