<%-- 
    Document   : OrganizationLookup
    Created on : Oct 18, 2018, 6:54:20 PM
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
        <title>Organization information</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/CustomStyles.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1 style = "text-align: center">Organization Lookup!</h1>
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
            <div class="col-md-10 col-sm-offset-1">
                <h4 style="text-align: center">
                    Check out the list of Organizations in our database already!  You can add a new one, or click on an existing one.  
                    After clicking the box should light up and you can click 'inquiry' to learn more or change it.
                    <hr>
                </h4>
            </div>

                <!--create buttons for selecting / showing things.-->
               <div class="col-md-10 ">
                <div>
                    <table id="powerTable" class="table table-hover">
                        <tr>
                            <th width="25%"> Organization name </th>
                            <th width="20%"> Description</th>
                            <th width="15%"> Phone</th>
                            <th width="20%"> Location Name</th>
                            <th width="10%"> </th>
                            <th width="10%"> </th>
                        </tr>
                        <c:forEach items="${orgs}" var="org" >
                            <tr>
                            <div class="button col-sm-4">
                                <td>
                                    <c:out value="${org.getName()}" />
                                </td>
                                <td>
                                    <c:out value="${org.getDescription()}" />
                                </td>
                                <td>
                                    <c:out value="${org.getPhone()}" />
                                </td>
                                <td>
                                    <c:out value="${org.getLocation().getName()}" />
                                </td>
                                <td>
                                    <a href="displayUpdateOrganization?organizationId=${org.getId()}">
                                        Edit
                                    </a>
                                </td>
                                <td>
                                    <a href="deleteOrganization?organizationId=${org.getId()}">
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
                        <a href="${pageContext.request.contextPath}/Hero/CreateNewOrganization">
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
