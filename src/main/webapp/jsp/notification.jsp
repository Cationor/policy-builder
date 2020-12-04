<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.contentpage"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="title.notification"/></title>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200;0,300;0,400;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,600;1,700;1,800;1,900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Dark.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Pretty-Registration-Form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/fontawesome-all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
</head>

<body id="page-top">
<c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/>
<header class="masthead" id="mastHeadCars">
    <div class="intro-body">
        <div class="row register-form" id="notificationPage">
            <div class="col-12 label-column">
            <c:if test="${not empty successfulActivation}">
                <c:if test="${successfulActivation}">
                    <label class="alert-success"><fmt:message key="notification.activated_user"/></label>
                </c:if>
                <c:if test="${!successfulActivation}">
                    <label class="alert-warning"><fmt:message key="notification.not_activated_user"/></label>
                </c:if>
            </c:if>
            <c:if test="${not empty successfulOrdering}">
                <c:if test="${successfulOrdering}">
                    <label class="alert-success"><fmt:message key="notification.ordering_car"/></label>
                </c:if>
                <c:if test="${!successfulOrdering}">
                    <label class="alert-warning"><fmt:message key="notification.not_ordering_car"/></label>
                </c:if>
            </c:if>
            <c:if test="${not empty successfulRegistration && successfulRegistration}">
                    <label class="alert-success"><fmt:message key="notification.registered_user"/></label>
            </c:if>
            <c:if test="${accessDenied}">
                <label class="alert-danger"><fmt:message key="notification.access_denied"/></label>
            </c:if>
            <c:if test="${activateAccount}">
                <label class="alert-warning"><fmt:message key="notification.activate_account"/></label>
            </c:if>
            <c:if test="${accountBlocked}">
                <label class="alert-danger"><fmt:message key="notification.account_blocked"/></label>
            </c:if>
            <c:if test="${not empty noCar && !noCar}">
                <label class="alert-danger"><fmt:message key="notification.car_not_found"/></label>
            </c:if>
            <c:if test="${negativeAmount}">
                <label class="alert-danger"><fmt:message key="notification.order_amount_error"/></label>
            </c:if>
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
</body>
</html>


