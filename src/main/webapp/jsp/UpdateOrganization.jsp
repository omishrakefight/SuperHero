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
        <title>Sightings</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/CustomStyles.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1 style = "text-align: center">Organization Update!</h1>
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
            <h1>Updating ${org.getName()}!</h1>

            <form class="form-horizontal" 
                  role="form" method="POST" 
                  action="updateOrganization">
                <div class="form-group">
                    <label for="add-name" class="col-md-4 control-label">Name:</label>
                    <div class="col-md-8">
                        <input type="text" value="${org.getName()}" class="form-control" name="Name" placeholder="${org.getName()}"/>
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-description" class="col-md-4 control-label">Description:</label>
                    <div class="col-md-8">
                        <input type="text" value="${org.getDescription()}" class="form-control" name="Description" placeholder="${org.getDescription()}"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="add-description" class="col-md-4 control-label">Phone:</label>
                    <div class="col-md-8">
                        <input type="text" value="${org.getPhone()}" class="form-control" name="Phone" placeholder="${org.getPhone()}"/>
                    </div>
                </div>

                <div class="col-lg-12">
                    <div class="col-md-6" id="old-Orgs">
                        <label for="" class="">Current Location</label>
                        <tr>
                            <td>
                                <ul class="list-group">
                                        <li class="list-group-item"> <c:out value="${org.getLocation().getName()}" />  </li>
                                </ul>
                            </td>
                        </tr>
                    </div>
                    <div class="form-group">
                        <div class="col-md-6">
                            <label for="add-affiliation" class="col-md-4 control-label">New Location</label>
                            <select class="form-control" id="Location" name="Location" placeholder="Location">
                                <c:forEach items="${locs}" var="loc">
                                    <option value="${loc.getId()}"> ${loc.getName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>


                <input type='hidden' name='id' value='${org.getId()}' />


                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update Organization" />
                    </div>
                </div>
            </form>

            <!-- Placed at the end of the document so the pages load faster -->
            <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
