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
                <div class = "col-md-10 col-sm-offset-1">
                    <div id= "hero1" class = "col-md-5" style="border-style: dotted">
                        <H2>
                            Hero 1!
                        </H2>
                    </div>
                    <!--Just adding blank space in the middle to split the data-->
                    <div class = "col-md-2" ></div>
                    <div id= "hero2" class = "col-md-5" style="border-style: dotted">
                        <H2>
                            Hero 2!
                        </H2>
                    </div>
                </div>
                <br>
                <div class = "col-md-10 col-sm-offset-1">
                    <div id= "hero3" class = "col-md-5" style="border-style: dotted">
                        <H2>
                            Hero 3!
                        </H2>
                    </div>
                    <!--Just adding blank space in the middle to split the data-->
                    <div class = "col-md-2" ></div>
                    <div id= "hero4" class = "col-md-5" style="border-style: dotted">
                        <H2>
                            Hero 4!
                        </H2>
                    </div>
                </div>
                <br>
                <div class = "col-md-10 col-sm-offset-1">
                    <div id= "hero5" class = "col-md-5" style="border-style: dotted">
                        <H2>
                            Hero 5!
                        </H2>
                    </div>
                    <!--Just adding blank space in the middle to split the data-->
                    <div class = "col-md-2" ></div>
                    <div id= "hero6" class = "col-md-5" style="border-style: dotted">
                        <H2>
                            Hero 6!
                        </H2>
                    </div>
                </div>
                <br><br><br><br><br><br>
                <hr/>
                <div id= "hero7" class = "col-md-10 col-sm-offset-1">
                    <div class = "col-md-5" style="border-style: dotted">
                        <H2>
                            Hero 7!
                        </H2>
                    </div>
                    <!--Just adding blank space in the middle to split the data-->
                    <div class = "col-md-2" ></div>
                    <div id= "hero8" class = "col-md-5" style="border-style: dotted">
                        <H2>
                            Hero 8!
                        </H2>
                    </div>
                </div>
                <br>
                <div class = "col-md-10 col-sm-offset-1">
                    <div id= "hero9" class = "col-md-5" style="border-style: dotted">
                        <H2>
                            Hero 9!
                        </H2>
                    </div>
                    <!--Just adding blank space in the middle to split the data-->
                    <div class = "col-md-2" ></div>
                    <div id= "hero10" class = "col-md-5" style="border-style: dotted">
                        <H2>
                            Hero 10!
                        </H2>
                    </div>
                </div>

            </div>
        </div>
        <!-- Placed at the end of the document so the pages load faster -->
        <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    </body>
</html>