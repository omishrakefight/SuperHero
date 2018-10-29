<%-- 
    Document   : HeroLookup
    Created on : Oct 18, 2018, 6:53:25 PM
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
        <title>Hero Lookup!</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/CustomStyles.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1 style = "text-align: center">Hero Lookup!</h1>
            <hr>
            <div class="navbar">
                <ul class="nav nav-tabs">
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/Home">Home</a></li>
                    <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/Hero/HeroList">Hero Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PowerList">Power Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/OrganizationList">Organization Lookup!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PlacesList">Places of Interest!</a></li>
                    <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/SightingList">Sightings!</a></li>
                </ul>    
            </div>
            <div class="col-md-10 col-sm-offset-1">
                <h4 style="text-align: center">
                    Check out the list of heroes in our database already!  You can add a new one, or click on an existing one.  
                    After clicking the box should light up and you can click 'inquiry' to learn more or change it.
                    <hr>
                </h4>
            </div>
                <div class="col-md-12">
                    <table id="powerTable" class="table table-hover">
                        <tr>
                            <th width="20%"> Super Hero Name </th>
                            <th width="20%"> Super Hero Powers</th>
                            <th width="20%"> Hero Description</th>
                            <th width="20%"> Organizations</th>
                            <th width="10%"></th>
                            <th width="10%"></th>
                        </tr>
                        <c:forEach items="${heroy}" var="heroSHIMA" >
                            <tr>
                                <td>
                                    <c:out value="${heroSHIMA.getName()}" />
                                </td>
                                <td>
                                    <ul class="list-group">
                                        <c:forEach items="${heroSHIMA.getSuperPowers()}" var= "pow">
                                            <li class="list-group-item"> <c:out value="${pow.getName()}" />  </li>
                                            </c:forEach>
                                    </ul>
                                </td>
                                <td>
                                    <c:out value="${heroSHIMA.getDescription()}" />
                                </td>
                                <td>
                                    <ul class="list-group">
                                        <c:forEach items="${heroSHIMA.getOrganizations()}" var= "org">
                                            <li class="list-group-item"> <c:out value="${org.getName()}" />  </li>
                                            </c:forEach>
                                    </ul>
                                </td>
                                
                                <td>
                                    <a href="displayUpdateHero?heroId=${heroSHIMA.getId()}">
                                        Edit
                                    </a>
                                </td>
                                <td>
                                    <a href="deleteHero?heroId=${heroSHIMA.getId()}">
                                        Delete
                                    </a>
                                </td>
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
                        <a href="${pageContext.request.contextPath}/Hero/CreateNewHero" class="button">
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
