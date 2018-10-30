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
            <h1 style = "text-align: center">Sighting Update!</h1>
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
            <h1>Sighting update..!</h1>
            
            <form class="form-horizontal" 
                  role="form" method="POST" 
                  action="updateSighting">
                
                
                <div class="form-group">
                    <label for="add-date" class="col-md-12 control-label">Current sighting date: ${date} </label>
                    <label for="add-date" class="col-md-4 control-label">Date of sighting</label>
                    <div class="col-md-8">
                        <input class="form-control" defaultDate="${date} "name="date" type="date" placeholder="2011-08-19">
                    </div>
                </div>
                <div class="form-group">
                    <label for="add-time" class="col-md-4 control-label">Time of sighting</label>
                    <div class="col-md-8">
                        <input class="form-control" value="${time}" name="time" type="text" placeholder="hh/mm/ss">
                    </div>
                </div>

                <div class="col-lg-12">
                    <div class="col-md-6" id="old-Orgs">
                        <label for="old-Orgs" class="">Current Location</label>
                        <tr>
                            <td>
                                <ul class="list-group">
                                        <li class="list-group-item"> <c:out value="${sight.getLocation().getName()}" />  </li>
                                </ul>
                            </td>
                        </tr>
                    </div>
                    <div class="form-group">
                        <div class="col-md-6">
                            <label for="add-affiliation" class="col-md-4 control-label">New Location</label>
                            <select class="form-control" name="Location" placeholder="Organizations">
                                <c:forEach items="${locs}" var="loc">
                                    <option value="${loc.getId()}"> ${loc.getName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>


                <div class="col-lg-12">
                    <div class="col-md-6" id="old-Orgs">
                        <tr>
                            <td>
                                <ul class="list-group">
                                    <label for="" class="control-label">Current Heroes </label>
                                    <c:forEach items="${heroesSighted}" var= "HS">
                                        <li class="list-group-item"> <c:out value="${HS.getName()}" />  </li>
                                        </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </div>
                    <div class="form-group">
                        <div class="col-md-6">
                            <label for="add-affiliation" class=" control-label">New Heroes </label>
                            <select multiple class="form-control" id="newPowers" name="newHeroes" placeholder="newHeroes">
                                <c:forEach items="${heroes}" var="hero">
                                    <option value="${hero.getId()}"> ${hero.getName()}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>

                <input type='hidden' name='id' value='${sight.getId()}' />


                <div class="form-group">
                    <div class="col-md-offset-4 col-md-8">
                        <input type="submit" class="btn btn-default" value="Update Sighting" />
                    </div>
                </div>
            </form>

            <!-- Placed at the end of the document so the pages load faster -->
            <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
            <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
