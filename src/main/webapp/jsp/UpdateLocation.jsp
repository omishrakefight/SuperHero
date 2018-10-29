<%-- 
    Document   : UpdateHero
    Created on : Oct 22, 2018, 7:22:44 PM
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
        <title>Location update</title>
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
            <h1>${loc.getName()} Update!</h1>


            <form class="form-horizontal" 
                  role="form" method="POST" 
                  action="updateLocation">
                <div class="form-group">
                    <div class="form-group">
                        <label for="add-latitude" class="col-md-4 control-label">Latitude:</label>
                        <div class="col-md-8">
                            <input type="text" value="${loc.getLatitude()}" class="form-control" name="Latitude" placeholder="Latitude"/>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="add-longitude" class="col-md-4 control-label">Longitude:</label>
                        <div class="col-md-8">
                            <input type="text" value="${loc.getLongitude()}" class="form-control" name="Longitude" placeholder="Longitude"/>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="add-name" class="col-md-4 control-label">Name:</label>
                        <div class="col-md-8">
                            <input type="text" value="${loc.getName()}" class="form-control" name="Name" placeholder="Name"/>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="add-description" class="col-md-4 control-label">Description:</label>
                        <div class="col-md-8">
                            <input type="text" value="${loc.getDescription()}" class="form-control" name="Description" placeholder="Description"/>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="add-state" class="col-md-4 control-label">State:</label>
                        <div class="col-md-8">
                            <input type="text" value="${loc.getState()}" class="form-control" name="State" placeholder="State"/>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="add-county" class="col-md-4 control-label">County:</label>
                        <div class="col-md-8">
                            <input type="text" value="${loc.getCounty()}" class="form-control" name="County" placeholder="County"/>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="add-street" class="col-md-4 control-label">Street:</label>
                        <div class="col-md-8">
                            <input type="text" value="${loc.getStreet()}" class="form-control" name="Street" placeholder="Street"/>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="add-houseNumber" class="col-md-4 control-label">House Number</label>
                        <div class="col-md-8">
                            <input type="text" value="${loc.getHouseNumber()}" class="form-control" name="HouseNumber" placeholder="HouseNumber"/>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="add-zipcode" class="col-md-4 control-label">ZipCode</label>
                        <div class="col-md-8">
                            <input type="text" value="${loc.getZipCode()}" class="form-control" name="ZipCode" placeholder="ZipCode"/>
                        </div>
                    </div>

                    <input type='hidden' name='id' value='${loc.getId()}' />


                    <div class="form-group">
                        <div class="col-md-offset-4 col-md-8">
                            <input type="submit" class="btn btn-default" value="Update Location" />
                        </div>
                    </div>
            </form>


            <!-- Placed at the end of the document so the pages load faster -->
            <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
