<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" "http://java.sun.com/xml/ns/j2ee/web-jsptaglibrary_2_0.xsd">

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page isELIgnored="false" %>

<html>
<head>
<link href="stylesheets/style.css" rel="stylesheet" type="text/css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
<title>Good Eatin' V1.0</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<div id="page">
    <div id="page-bgtop">
        <h2><a href="/newRestaurant.jsp">Add a restaurant</a></h2>
        <div id="content">
            <c:forEach var="restaurant" items="${requestScope.restaurants}">
                <div class="post">
                    <p class="meta"><span class="date">${restaurant.dateAdded}</span>
                        <c:if test="${restaurant.submitter != null}">Submitted by ${restaurant.submitter.nickname}</c:if>
                    </p>
                    <h2 class="title"><c:out value="${restaurant.name}"/></h2>
                    <div class="entry">
                        <c:out value="${restaurant.description}"/>
                    </div>
                    <p><a href="/newComment.jsp?restaurantId=${restaurant.id.id}">Add a comment</a></p>
                    <c:forEach var="comment" items="${restaurant.comments}">
                        <div class="comments"><c:out value="${comment.commentText}"></c:out></div><br/>
                    </c:forEach>                        
                </div>
            </c:forEach>
        </div>
            <div style="clear: both;">&nbsp;</div>
    </div>
    <div id="footer">
        <p>Copyright (c) 2010 Element 84.Design by <a href="http://www.freecsstemplates.org/">Free CSS Templates</a>.</p>
    </div>
</div>
</body>
</html>
