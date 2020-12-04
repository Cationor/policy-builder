<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ptg" uri="paginationtags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.contentpage"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="title.cars"/></title>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200;0,300;0,400;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,600;1,700;1,800;1,900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Dark.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Pretty-Registration-Form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://bootstraptema.ru/plugins/2018/irs/ion.rangeSlider.css"/>
    <link rel="stylesheet" href="https://bootstraptema.ru/plugins/2018/irs/ion.rangeSlider.skin.css"/>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
</head>

<body id="page-top">
<c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/>
<header class="masthead" id="mastHeadCars">
    <div class="intro-body">
        <div class="row register-form" id="adminPage">
            <div class="col-3">
                <div class="row" id="notificationMessage">
                    <div class="label-column" style="margin:auto">
                            <c:if test="${requestScope.carAdded}">
                                <label class="col-form-label alert-success" style="padding:0 10px"><fmt:message key="admin_cars.created_car"/></label>
                            </c:if>
                        <c:if test="${not empty requestScope.carUpdated}">
                            <c:if test="${requestScope.carUpdated}">
                                <label class="col-form-label alert-success" style="padding:0 10px"><fmt:message key="admin_cars.updated_car"/></label>
                            </c:if>
                            <c:if test="${!requestScope.carUpdated}">
                                <label class="col-form-label alert-warning" style="padding:0 10px"><fmt:message key="admin_cars.not_updated_car"/></label>
                            </c:if>
                        </c:if>
                    </div>
                </div>
                <form action="CarBook" method="post" class="shadow-none custom-form" id="carsFilter"
                      style="font-family: Nunito">
                    <div class="form-row form-group">
                        <div class="col-12 label-column">
                            <label class="col-form-label" id="labelPage"><fmt:message key="label.cars"/></label>
                        </div>
                        <div class="col-4 label-column">
                            <label class="col-form-label"><fmt:message key="admin_cars.type"/></label></div>
                        <div class="col-7 dropdown input-column">
                            <select class="form-control" name="car_type">
                                <option value=""><fmt:message key="admin_cars.car_type.any"/></option>
                                <option value="SUV"><fmt:message key="admin_cars.car_type.suv"/></option>
                                <option value="SEDAN"><fmt:message key="admin_cars.car_type.sedan"/></option>
                                <option value="MINIVAN"><fmt:message key="admin_cars.car_type.minivan"/></option>
                            </select>
                        </div>
                        <div class="col-4 label-column">
                            <label class="col-form-label"><fmt:message key="admin_cars.status"/></label></div>
                        <div class="col-7 dropdown input-column">
                            <select class="form-control" name="is_available" id="car-status-select">
                                <option value=""><fmt:message key="admin_cars.status.any"/></option>
                                <option value="true"><fmt:message key="admin_cars.status.available"/></option>
                                <option value="false"><fmt:message key="admin_cars.status.not_available"/></option>
                            </select>
                        </div>
                        <div class="col-12 input" id="filterbutton">
                            <button class="submit-button" type="submit" id="butt">
                                <fmt:message key="admin_cars.filter"/></button>
                        </div>
                    </div>
                    <input type="hidden" name="command" value="filter_cars">
                    <a class="shadow-sm" href="CarBook?command=move_create_car_page">
                        <fmt:message key="admin_cars.add"/></a>
                </form>
            </div>
            <!----------------------------------------------->
            <div class="col-8">
                <div class="form-row form-group" id="carElement">
                    <c:choose>
                    <c:when test="${not empty sessionScope.carList}">
                        <table class="table">
                            <ptg:admin-cars-pagination/>
                        </table>
                    </c:when>
                    <c:otherwise>
                    <c:if test="${!requestScope.carAdded}">
                    <c:if test="${empty noCars}">
                        <label class="col-form-label"><fmt:message key="admin_cars.choose_params"/></label></div>
                    </c:if>
                    <c:if test="${not empty noCars && !noCars}">
                        <label class="col-form-label"><fmt:message key="admin_cars.nothing_to_show"/></label></div>
                    </c:if>
                    </c:if>
        </c:otherwise>
        </c:choose>
        </div>
    </div>
</header>
<c:import url="${pageContext.request.contextPath}/jsp/fragment/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/date_range.js"></script>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/js/grayscale.js"></script>
<script src="${pageContext.request.contextPath}/js/moment.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</body>
</html>


