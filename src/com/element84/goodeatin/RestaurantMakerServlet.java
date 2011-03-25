package com.element84.goodeatin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class RestaurantMakerServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		EntityManager em = EMF.get().createEntityManager();
		try {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			Restaurant r = new Restaurant();
			r.setName("Brett's House");
			r.setDescription("Great food, pretty good company... sometimes.");
			r.setAddress("123 South St.");
			r.setDateAdded(new Date());
			Comment c = new Comment();
			c.setCommentText("Here is our first comment");
			r.setComments(Arrays.asList(c));
			
			em.persist(r);
			tx.commit();
		
		tx = em.getTransaction();
		tx.begin();
		r = new Restaurant();
		r.setName("Bob's BBQ");
		r.setDescription("This place rocks!");
		r.setDateAdded(new Date());
		c = new Comment();
		c.setCommentText("Here is our first comment");
		Comment c2 = new Comment();
		c2.setCommentText("Here is the second comment.");		
		r.setComments(Arrays.asList(c, c2));
		em.persist(r);
		tx.commit();
		
		tx = em.getTransaction();
		tx.begin();
		r = new Restaurant();
		r.setName("Potato House");
		r.setDescription("This place has the biggest potatoes I've ever seen.  Seriously.");
		r.setDateAdded(new Date());
		c = new Comment();
		c.setCommentText("Here is another comment");
		r.setComments(Arrays.asList(c));
		em.persist(r);
		tx.commit();
		}
		finally {
			em.close();
		}
		
		resp.getWriter().print("<b>Done...</b>");
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException, ServletException {
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			Restaurant r = new Restaurant();
			r.setName(req.getParameter("name"));
			r.setDescription(req.getParameter("description"));
			r.setAddress(req.getParameter("address"));
			r.setDateAdded(new Date());
			r.setSubmitter(UserServiceFactory.getUserService().getCurrentUser());
			em.persist(r);
			
			// Clear memcache so users see the new restaurant.
			MemcacheService memcache = MemcacheServiceFactory.getMemcacheService();
			memcache.delete("top10restaurants");
		}
		finally {
			em.close();
		}
		
		resp.sendRedirect("/goodeatin");
	}
}
