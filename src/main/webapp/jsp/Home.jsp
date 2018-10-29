<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Home Page</title>
        <!-- Bootstrap core CSS -->
        <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/css/CustomStyles.css" rel="stylesheet">
    </head>
    <body>
        <div class="container">
            <h1 style = "text-align: center">
                Welcome to Hero Sighter home page!
            </h1>
            <hr/>
            <div class="navbar">
                <div class="navbar">
                    <ul class="nav nav-tabs">
                        <li role="presentation" class="active"><a href="${pageContext.request.contextPath}/Hero/Home">Home</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/HeroList">Hero Lookup!</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PowerList">Power Lookup!</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/OrganizationList">Organization Lookup!</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/PlacesList">Places of Interest!</a></li>
                        <li role="presentation"><a href="${pageContext.request.contextPath}/Hero/SightingList">Sightings!</a></li>
                    </ul>    
                </div>

                <!--border border-primary style = "outline: 1px solid orange-->
                <div style="border-style: dashed ">
                    <h4>
                        Welcome to Hero Sighter!  The most professional, as well as up to date, Hero sighting catalog website!
                        Our mission here is to log every sighting of every hero and villain known, and if they are unknown then make them known!
                        We log known powers of each person, what organizations they are associated with, and where they are seen.
                        Using low level intelligence and out website, one can put dots on any map to see where certain heros (or villains) frequent.
                        This can either let you see your favorite hero, or avoid the most evil villains. The choice. . . is yours...
                        Please subscribe to help give back to the community.  Remove adblocker cause we want money.
                    </h4>
                </div>
                <br>
                <br>
                <!--                // do database limit 10 and copy fpr each from vendingMachine-->



                <div class="col-md-12">
                <!--<table id = "heroOrganizaer" class="table table-hover">-->
                    <tr>
                        <th width="50%"></th> 
                        <!--                        <th width="10%"></th> -->
                        <th width="50%"></th> 
                    </tr>
                    <c:forEach items="${sights}" var="sight" >
                        <!--<tr>-->
                            <div class="col-md-6">
                                <table id="powerTable" class="table table-hover">
                                    <tr>
                                        <th width="30%"> Hero </th>
                                        <th width="40%"> Location</th>
                                        <th width="30%"> Time</th>

                                    </tr>
                                    <tr>
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

                                        </div>
                                    </tr>
                                </table>
                            </div>
                        <!--</tr>-->
                    </c:forEach>
                <!--</table>-->
                </div>



            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>