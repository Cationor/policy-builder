<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.contentpage"/>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="title.home"/></title>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200;0,300;0,400;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Dark.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Pretty-Registration-Form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
</head>

<body id="page-top">
<c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/>
<header class="masthead">
    <div class="shadow intro-body">
        <div class="container" id="carouselMainContainer">
        <div id="carouselMain" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
                <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
                <li data-target="#carouselExampleIndicators" data-slide-to="3"></li>
            </ol>
            <div class="carousel-inner" id="carouselMainInner">
                <div class="carousel-item active">
                    <img class="d-block w-100 " src="${pageContext.request.contextPath}/img/slide_1.png">
                    <div class="carousel-caption d-none d-md-block">
                        <h3><fmt:message key="home.slide_1.label"/></h3>
                        <p><fmt:message key="home.slide_1.text"/></p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100 " src="${pageContext.request.contextPath}/img/slide_2.png">
                    <div class="carousel-caption d-none d-md-block">
                        <h3><fmt:message key="home.slide_2.label"/></h3>
                        <p><fmt:message key="home.slide_2.text"/></p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100 " src="${pageContext.request.contextPath}/img/slide_3.png">
                    <div class="carousel-caption d-none d-md-block">
                        <h3><fmt:message key="home.slide_3.label"/></h3>
                        <p><fmt:message key="home.slide_3.text"/></p>
                    </div>
                </div>
                <div class="carousel-item">
                    <img class="d-block w-100 " src="${pageContext.request.contextPath}/img/slide_4.png">
                    <div class="carousel-caption d-none d-md-block">
                        <h3><fmt:message key="home.slide_4.label"/></h3>
                        <p><fmt:message key="home.slide_4.text"/></p>
                    </div>
                </div>
            </div>
            <a class="carousel-control-prev" href="#carouselMain" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselMain" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
        </div>
        <div class="container" id="pics">
        <div class="row" id="logosFeat">
             <div class="col"><i class="fas fa-car"></i>
             <figure class="figure"></figure></div>
             <div class="col"><i class="far fa-credit-card"></i></div>
             <div class="col"><i class="far fa-handshake"></i></div>
             <div class="col"><i class="far fa-calendar-check"></i></div>
        </div>
        <div class="row">
            <div class="col"><label class="col-form-label text-center" style="font-size: 25px;"><fmt:message key="home.mark_1.text"/></label></div>
            <div class="col"><label class="col-form-label text-center" style="font-size: 25px;"><fmt:message key="home.mark_2.text"/></label></div>
            <div class="col"><label class="col-form-label text-center" style="font-size: 25px;"><fmt:message key="home.mark_3.text"/></label></div>
            <div class="col"><label class="col-form-label" style="font-size: 25px;"><fmt:message key="home.mark_4.text"/></label></div>
        </div>
        </div>
    </div>
</header>

<c:import url="${pageContext.request.contextPath}/jsp/fragment/footer.jsp"/>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/js/grayscale.js"></script>
</body>

</html>
