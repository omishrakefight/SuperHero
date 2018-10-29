<%-- 
    Document   : Sightings
    Created on : Oct 18, 2018, 6:55:15 PM
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
        <title>Sightings</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/CustomStyles.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1 style = "text-align: center">Sighting Lookups!</h1>
            <hr>
            <div class="navbar" style="text-align: center">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/Home">Home</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/HeroList">Hero Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PowerList">Power Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/OrganizationList">Organization Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PlacesList">Places of Interest!</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/Hero/SightingList">Sightings!</a></li>
                </ul>    
            </div>
            <div class="col-md-10 col-sm-offset-1">
                <h4 style="text-align: center">
                    Check out the list of Sightings in our database already!  You can add a new one, or click on an existing one.  
                    After clicking the box should light up and you can click 'inquiry' to learn more or change it.
                </h4>
                <hr>
                
                <div>
                    <table id="powerTable" class="table table-hover">
                        <tr>
                            <th width="20%"> Hero </th>
                            <th width="20%"> Location</th>
                            <th width="20%"> Time</th>
                            <th width="20%"> </th>
                            <th width="20%"> </th>
                        </tr>
                        <c:forEach items="${sights}" var="sight" >
                            <tr>
                            <div class="button col-sm-4">
                                <td>
                                    <ul class="list-group">
                                        <c:forEach items="${sight.getHeroesSeen()}" var= "hero">
                                            <li class="list-group-item"> <c:out value="${hero.getName()}" />  </li>
                                        </c:forEach>
                                    </ul>
                                </td>
                                <td>
                                    <c:out value="${sight.getLocation().getName()}" />
                                </td>
                                <td>
                                    <c:out value="${sight.getDateTime()}" />
                                </td>
                                <td>
                                    <a href="displayUpdateSighting?sightingId=${sight.getId()}">
                                        Edit
                                    </a>
                                </td>
                                <td>
                                    <a href="deleteSighting?sightingId=${sight.getId()}">
                                        Delete
                                    </a>
                                </td>
                            </div>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
                
                <div class="col-sm-12">
                    <div class="col-md-6" style="text-align: center">
                        <button  >
                            Select
                        </button>
                    </div>
                    <div class="col-md-6" style="text-align: center">
                        <a href="${pageContext.request.contextPath}/Hero/CreateNewSighting">
                        <button> Create new </button>
                        </a>
                    </div>
                </div>
            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
