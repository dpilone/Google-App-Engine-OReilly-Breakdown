<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    

<%@page import="com.google.appengine.api.users.UserService"%>
<%@page import="com.google.appengine.api.users.UserServiceFactory"%><div id="header-wrapper">
    <div id="header">
        <div id="menu">
            <ul>
                <li><a href="#" class="first">Home</a></li>
                <li class="current_page_item"><a href="#">Top 10</a></li>
                <li><a href="#">About</a></li>
                <li><a href="#">Contact</a></li>
                <c:choose>
                    <c:when test="${pageContext.request.userPrincipal == null}">
                        <li><a href="<%= UserServiceFactory.getUserService().createLoginURL("/goodeatin")%>">Sign In</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="<%= UserServiceFactory.getUserService().createLogoutURL("/goodeatin") %>">Sign Out, ${pageContext.request.userPrincipal.name}</a></li>
                    </c:otherwise> 
                </c:choose>
            </ul>
        </div>
        <!-- end #menu -->
        <div id="search">
            <form method="get" action="">
                <fieldset>
                    <input type="text" name="s" id="search-text" size="15" />
                    <input type="submit" id="search-submit" value="GO" />
                </fieldset>
            </form>
        </div>
        <!-- end #search -->
    </div>
</div>
<!-- end #header -->

<div id="logo">
    <h1><a href="#">Good Eatin'</a></h1>
    <p><em>Food you wished you ate a long time ago...</em></p>
</div>
<hr />

<!-- end #logo -->
    