<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page isELIgnored="false" %>

<html>
<head>
<link href="stylesheets/style.css" rel="stylesheet" type="text/css" media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Add a new restaurant</title>
</head>
<body>
<jsp:include page="header.jsp"/>

<div id="page">
    <div id="page-bgtop">
        <div id="content">
            <h2 class="title">Add a new comment</h2>

<form action="/asynchronousCommentMaker" method="post">
    <label for="name">Comment:</label><br/>
    <textarea rows="5" cols="40" name="comment" id="comment"></textarea><br/>
    <input type="hidden" name="restaurantId" value="${param.restaurantId}"/>
    <br/>
 
    <input type="submit" value="Submit"/>
</form>

        </div>
        <div style="clear: both;">&nbsp;</div>
    </div>
    <div id="footer">
        <p>Copyright (c) 2010 Element 84.Design by <a href="http://www.freecsstemplates.org/">Free CSS Templates</a>.</p>
    </div>
</div>
</body>
</html>