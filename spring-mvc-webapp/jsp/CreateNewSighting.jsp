<%-- 
    Document   : CreateNewSighting
    Created on : Oct 19, 2018, 10:35:35 AM
    Author     : omish
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>New Sighting</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/CustomStyles.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1 style = "text-align: center">Tell us where someone currently is!!</h1>
            <hr>
            <div class="navbar" >
                <ul class="nav nav-tabs" style="align-content: center">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/Home">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/HeroList">Hero Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PowerList">Power Lookup!</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/Hero/OrganizationList">Organization Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PlacesList">Places of Interest!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/SightingList">Sightings!</a></li>
                </ul>    
            </div>
            <h2>Add New Organization!</h2>
            <form class="form-horizontal" 
                  role="form" method="POST" 
                  action="createSighting">
                <div class="form-group">
                    <label for="add-date" class="col-md-4 control-label">Date of sighting</label>
                    <div class="col-md-8">
                        <input class="form-control" name="date" type="date" placeholder="2011-08-19" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-time" class="col-md-4 control-label">Time of sighting</label>
                    <div class="col-md-8">
                        <input class="form-control" name="time" type="text" placeholder="hh/mm/ss" required>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-locationid" class="col-md-4 control-label">Location: </label>
                    <div class="col-md-8">
                        <select class="form-control" id="Affiliations" name="LocationId" placeholder="LocationId" required>
                            <c:forEach items="${Locations}" var="loc">
                                <option value="${loc.getId()}"> ${loc.getName()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-heroes" class="col-md-4 control-label">Select Heroes!</label>
                    <div class="col-md-8">
                        <select multiple class="form-control" id="Heroes"name="Heroes" placeholder="Heroes" required>
                            <c:forEach items="${Heroes}" var="Hero">
                                <option value="${Hero.getId()}"> ${Hero.getName()} </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Create Sighting!"/>
                    </div>
                </div>
            </form>
                
                
    </div><!-- Placed at the end of the document so the pages load faster -->
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

</body>
</html>
