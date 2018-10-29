<%-- 
    Document   : CreateNewPlaceOfInterest
    Created on : Oct 19, 2018, 10:35:07 AM
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
        <title>Add a place of Interest</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/CustomStyles.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1 style = "text-align: center">Add a new Place of Interest!  Think someone will come to this place?
                Or simply saw someone but can't confirm who it was?  Go ahead and add the place to watch!</h1>
            <hr>
            <div class="navbar" >
                <ul class="nav nav-tabs" style="align-content: center">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/Home">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/HeroList">Hero Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PowerList">Power Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/OrganizationList">Organization Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PlacesList">Places of Interest!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/SightingList">Sightings!</a></li>
                </ul>
            </div>
            <h2>Add a New Place!</h2>
            <form class="form-horizontal" 
                  role="form" method="POST" 
                  action="createLocation">
                <div class="form-group">
                    <label for="add-latitude" class="col-md-4 control-label">Latitude:</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" name="Latitude" placeholder="Latitude" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-longitude" class="col-md-4 control-label">Longitude:</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" name="Longitude" placeholder="Longitude" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-name" class="col-md-4 control-label">Name:</label>
                    <div class="col-md-8">
                            <input type="text" class="form-control" name="Name" placeholder="Name" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-description" class="col-md-4 control-label">Description:</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" name="Description" placeholder="Description" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-state" class="col-md-4 control-label">State:</label>
                    <div class="col-md-8">
                            <input type="text" class="form-control" name="State" placeholder="State" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-county" class="col-md-4 control-label">County:</label>
                    <div class="col-md-8">
                        <input type="text" class="form-control" name="County" placeholder="County" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-street" class="col-md-4 control-label">Street:</label>
                    <div class="col-md-8">
                            <input type="text" class="form-control" name="Street" placeholder="Street" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-houseNumber" class="col-md-4 control-label">House Number</label>
                    <div class="col-md-8">
                            <input type="text" class="form-control" name="HouseNumber" placeholder="HouseNumber" required/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-zipcode" class="col-md-4 control-label">ZipCode</label>
                    <div class="col-md-8">
                            <input type="text" class="form-control" name="ZipCode" placeholder="ZipCode" required/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Create Location"/>
                    </div>
                </div>
            </form>
        </div>

        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
