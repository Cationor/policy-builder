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
    <title><fmt:message key="title.orders"/></title>
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
        <div class="row register-form" id="clientOrderPage">
            <div class="col-12">
                <div class="row" id="notificationMessage">
                    <label id="labelPage">
                        <fmt:message key="label.orders"/>
                    </label>
                    <div class="label-column" style="margin:auto">
                        <c:if test="${requestScope.orderStatusUpdated}">
                            <label class="col-form-label alert-success" style="padding:0 10px"><fmt:message key="client_orders.successfully_paid"/></label>
                        </c:if>
                    </div>
                </div>
                <div class="form-row form-group register-form" id="orderElement">
                    <c:choose>
                    <c:when test="${not empty sessionScope.orderList}">
                        <table class="table table-sm" id="clientOrderTable">
                            <tbody>
                            <tr>
                                <th><fmt:message key="client_orders.table.car_model"/></th>
                                <th><fmt:message key="client_orders.table.date_from"/></th>
                                <th><fmt:message key="client_orders.table.date_to"/></th>
                                <th><fmt:message key="client_orders.table.amount"/></th>
                                <th colspan="2"><fmt:message key="client_orders.table.status"/></th>
                            </tr>
                            <ptg:client-orders-pagination/>
                            </tbody>
                        </table>
                    </c:when>
                        <c:otherwise>
                            <c:if test="${not empty noOrders && !noOrders}">
                                <label class="col-form-label"><fmt:message key="client_orders.nothing_to_show"/></label></div>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
            </div>
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


