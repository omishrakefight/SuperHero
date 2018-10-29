<%-- 
    Document   : InformationLocation
    Created on : Oct 24, 2018, 12:45:23 PM
    Author     : omish
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>Location information</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/CustomStyles.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1 style = "text-align: center">Location Update!</h1>
            <hr>
            <div class="navbar" style="text-align: center">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/Home">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/HeroList">Hero Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PowerList">Power Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/OrganizationList">Organization Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PlacesList">Places of Interest!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/SightingList">Sightings!</a></li>
                </ul>    
            </div>
            <h1>${loc.getName()} Specifics!</h1>



            <!--<div class="form-group">-->
            <div class="container">
                <div class="col-md-12">
                    <label for="add-latitude" class="col-md-4 control-label">Latitude:</label>
                    <div class="col-md-8">
                        <c:out value="${loc.getLatitude()}" />

                    </div>
                </div>
            

            <!--<div class="form-group">-->
            <div class="col-md-12">
                <label for="add-longitude" class="col-md-4 control-label">Longitude:</label>
                <div class="col-md-8">
                    <c:out value="${loc.getLongitude()}" />

                </div>
            </div>

            <div class="col-md-12">
                <!--<div class="form-group">-->
                <label for="add-name" class="col-md-4 control-label">Name:</label>
                <div class="col-md-8">
                    <c:out value="${loc.getName()}" />
                </div>
            </div>

            <div class="col-md-12">
                <!--<div class="form-group">-->
                <label for="add-description" class="col-md-4 control-label">Description:</label>
                <div class="col-md-8">
                    <c:out value="${loc.getDescription()}" />
                </div>
            </div>

            <div class="col-md-12">
                <!--<div class="form-group">-->
                <label for="add-state" class="col-md-4 control-label">State:</label>
                <div class="col-md-8">
                    <c:out value="${loc.getState()}" />
                </div>
            </div>

            <div class="col-md-12">
                <!--<div class="form-group">-->
                <label for="add-county" class="col-md-4 control-label">County:</label>
                <div class="col-md-8">
                    <c:out value="${loc.getCounty()}" />
                </div>
            </div>

            <div class="col-md-12">
                <!--<div class="form-group">-->
                <label for="add-street" class="col-md-4 control-label">Street:</label>
                <div class="col-md-8">
                    <c:out value="${loc.getStreet()}" />
                </div>
            </div>

            <div class="col-md-12">
                <!--<div class="form-group">-->
                <label for="add-houseNumber" class="col-md-4 control-label">House Number</label>
                <div class="col-md-8">
                    <c:out value="${loc.getHouseNumber()}" />
                </div>
            </div>

            <div class="col-md-12">
                <!--<div class="form-group">-->
                <label for="add-zipcode" class="col-md-4 control-label">ZipCode</label>
                <div class="col-md-8">
                    <c:out value="${loc.getZipCode()}" />
                </div>
            </div>
        </div>




        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
