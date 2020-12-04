<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.contentpage"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="title.car"/></title>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200;0,300;0,400;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Dark.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Pretty-Registration-Form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
</head>

<body id="page-top">
<c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/>
<header class="masthead">
    <div class="intro-body">
        <div class="row register-form" id="carPage">
            <div class="col-5" id="carouselOrder">
                <div class="container">
                    <div id="carouselMain" class="carousel slide" data-ride="carousel">
                        <ol class="carousel-indicators">
                            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                            <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
                        </ol>
                        <div class="carousel-inner" id="carouselMainInner">
                            <div class="carousel-item active">
                                <img class="d-block w-100 " src="/image/${car.carView.exterior}">
                            </div>
                            <div class="carousel-item">
                                <img class="d-block w-100 " src="/image/${car.carView.interior}">
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
            </div>
            <div class="col-6" id="orderDetails">
                <form action="CarBook" method="post" class="shadow-none custom-form" style="font-family: Nunito">
                    <table class="table table-sm" id="orderTable">
                        <tr>
                            <th><fmt:message key="order.date_from"/></th>
                            <td>${date_from}</td>
                            <th><fmt:message key="order.date_to"/></th>
                            <td>${date_to}</td>
                        </tr>
                        <tr>
                            <th><fmt:message key="order.client_first_name"/></th>
                            <td>${sessionScope.user.firstName}</td>
                            <th><fmt:message key="order.client_second_name"/></th>
                            <td>${sessionScope.user.secondName}</td>
                        </tr>
                        <tr>
                            <th colspan="1"><fmt:message key="order.car_model"/></th>
                            <td colspan="3">${car.model}</td>
                        </tr>
                        <tr>
                            <th><fmt:message key="order.car_type"/></th>
                            <td>${car.type}</td>
                            <th><fmt:message key="order.number_seats"/></th>
                            <td>${car.numberSeats}</td>
                        </tr>
                        <tr>
                            <th><fmt:message key="order.fuel"/></th>
                            <td>${car.fuelType}</td>
                            <th><fmt:message key="order.fuel_consumption"/></th>
                            <td>${car.fuelConsumption}</td>
                        </tr>
                        <tr>
                            <th><fmt:message key="order.amount"/></th>
                            <td>${order_amount}$</td>
                            <td></td>
                            <td>
                                <div class="form-row form-group">
                                    <div class="input-column" >
                                        <form action="CarBook" method="post" style="font-family: Nunito">
                                            <input type="hidden" name="car_id" value="${car.carId}">
                                            <input type="hidden" name="user_id" value="${sessionScope.user.userId}">
                                            <input type="hidden" name="date_from" value="${date_from}">
                                            <input type="hidden" name="date_to" value="${date_to}">
                                            <input type="hidden" name="amount" value="${order_amount}">
                                            <input type="hidden" name="command" value="order_car">
                                            <button class="submit-button" type="submit" id="buttOrd">
                                                <fmt:message key="order.order_car"/></button>
                                        </form>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</header>
<c:import url="${pageContext.request.contextPath}/jsp/fragment/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/js/grayscale.js"></script>
<script src="${pageContext.request.contextPath}/js/moment.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://bootstraptema.ru/plugins/2018/irs/ion.rangeSlider.min.js"></script>
<script src="${pageContext.request.contextPath}/js/price_range.js"></script>
</body>
</html>