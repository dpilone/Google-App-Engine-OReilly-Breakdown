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
            <h2 class="title">Add a new restaurant</h2>

<form action="/restaurantMaker" method="post">
    <label for="name">Restaurant name:</label><br/>
    <input name="name" id="name" type="text"/><br/>
    <br/>
    
    <label for="description">Description:</label><br/>
    <textarea rows="5" cols="40" name="description" id="description">Enter your description</textarea><br/>
    <br/>
    
    <label for="name">Address:</label><br/>
    <input name="address" id="address" type="text"/><br/>
    
    <input type="submit" value="Post It"/>
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