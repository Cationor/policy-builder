<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.contentpage"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="title.register"/></title>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200;0,300;0,400;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Dark.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Pretty-Registration-Form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
</head>

<body id="page-top" style="background-color: rgb(56,55,57);">
<c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/>
<header class="masthead">
    <div class="intro-body">
    <div class="row register-form" id="register_form">
    <div class="col-md-8 offset-md-2">
        <form action="CarBook" method="post" class="shadow-lg custom-form" id="register" style="background-color: rgba(0,0,0,0.08); font-family: Nunito">
            <c:if test="${not empty registerParameters}">
                <label class="alert-danger"><fmt:message key="register.invalid_parameters"/></label>
            </c:if>
            <c:if test="${userExist}">
                <label class="alert-warning"><fmt:message key="register.user_exist"/></label>
            </c:if>
            <div class="form-row form-group" style="margin: 0px -5px 15px;">
                <div class="col-sm-4 label-column">
                    <label class="col-form-label">Registered object</label></div>
                <div class="col-sm-4 input-column">
                    <input class="form-control" name="email" type="email"
                           autofocus required value="${registerParameters.get("email")}"
                           minlength="7" maxlength="255" onchange="this.setCustomValidity('')"
                           title="<fmt:message key="register.email"/>"
                           oninvalid="this.setCustomValidity('<fmt:message key="register.email.validation"/>')">
                </div>
                <c:if test='${not empty registerParameters && empty registerParameters.get("email")}'>
                    <div class="col-sm-4 input-column">
                        <label class="alert-danger"><fmt:message key="register.invalid_email"/></label>
                    </div>
                </c:if>
            </div>
            <div class="form-row form-group">
                <div class="col-sm-4 label-column">
                    <label class="col-form-label">Sum insured</label></div>
                <div class="col-sm-4 input-column">
                    <input class="form-control" name="password" type="password" minlength="8" maxlength="30" id="pass"
                           required pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{8,}"
                           oninvalid="this.setCustomValidity('<fmt:message key="register.password.validation"/>')"
                           onchange="this.setCustomValidity('')"
                           onkeyup="checkPasses()" title="<fmt:message key="register.password"/>">
                </div>
                <div class="col-sm-4 input-column" style="display: none" id="not_valid">
                    <label class="alert-danger"><fmt:message key="register.invalid_passwords"/></label>
                </div>
            </div>
            <div class="form-row form-group">
                <div class="col-sm-4 label-column">
                    <label class="col-form-label">Contract currency</label></div>
                <div class="col-sm-4 input-column">
                    <input class="form-control" name="confirm_password" type="password" minlength="8" maxlength="30" id="passConf"
                           required pattern="(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z!@#$%^&*]{7,}"
                           oninvalid="this.setCustomValidity('<fmt:message key="register.password.validation"/>')"
                           onchange="this.setCustomValidity('')"
                           onkeyup="checkPasses()" title="<fmt:message key="register.confirm_password"/>">
                </div>
            </div>
            <div class="form-row form-group">
                <div class="col-sm-4 label-column">
                    <label class="col-form-label">First name</label></div>
                <div class="col-sm-4 input-column">
                    <input class="form-control" name="first_name" type="text"
                           pattern="[a-zA-Zа-яА-Я-]{1,20}" required value="${registerParameters.get("first_name")}"
                           oninvalid="this.setCustomValidity('<fmt:message key="register.name.validation"/>')"
                           onchange="this.setCustomValidity('')" title="<fmt:message key="register.first_name"/>">
                </div>
                <c:if test='${not empty registerParameters && empty registerParameters.get("first_name")}'>
                    <div class="col-sm-4 input-column">
                        <label class="alert-danger"><fmt:message key="register.invalid_name"/></label>
                    </div>
                </c:if>
            </div>
            <div class="form-row form-group">
                <div class="col-sm-4 label-column">
                    <label class="col-form-label label-column">Second name</label>
                </div>
                <div class="col-sm-4 input-column">
                    <input class="form-control" name="second_name" type="text"
                           pattern="[a-zA-Zа-яА-Я-]{1,20}" required value="${registerParameters.get("second_name")}"
                           oninvalid="this.setCustomValidity('<fmt:message key="register.name.validation"/>')"
                           onchange="this.setCustomValidity('')" title="<fmt:message key="register.second_name"/>">
                </div>
                <c:if test='${not empty registerParameters && empty registerParameters.get("second_name")}'>
                    <div class="col-sm-4 input-column">
                        <label class="alert-danger"><fmt:message key="register.invalid_name"/></label>
                    </div>
                </c:if>
            </div>
            <div class="form-row form-group">
                <div class="col-sm-4 label-column"><label class="col-form-label">
                    Insurance coverage area</label></div>
                <div class="col-sm-4 input-column">
                    <input class="form-control" name="driver_license" type="text"
                           pattern="([0-9]?[a-zA-Z]{2}\s?[0-9]{6})"
                           required value="${registerParameters.get("driver_license")}"
                           title="<fmt:message key="register.driver_license"/>"
                           onchange="this.setCustomValidity('')"
                           oninvalid="this.setCustomValidity('<fmt:message key="register.driver_license.validation"/>')">
                </div>
                <c:if test='${not empty registerParameters && empty registerParameters.get("driver_license")}'>
                    <div class="col-sm-4 input-column">
                        <label class="alert-danger"><fmt:message key="register.invalid_drive_license"/></label>
                    </div>
                </c:if>
            </div>



            <div class="form-row form-group">
                <div class="col-sm-4 label-column"><label class="col-form-label">
                    Term of validity</label></div>
                <div class="col-4 input-column">
                    <input class="form-control form-control--date" type="date" name="date_from" id="df"
                           value="${sessionScope.carParameters.get("date_from")}" required onchange="this.setCustomValidity('')"
                           oninvalid="this.setCustomValidity('<fmt:message key="cars.date_from.validation"/>')">
                </div>
            </div>

            <div class="form-row form-group">
                <div class="col-sm-4 label-column"> <label class="col-form-label">Insurance type</label></div>
                <div class="col-sm-4 input-column"> <select class="form-control" name="order_status" id="orderSelect">
                    <option value="">Collision coverage</option>
                    <option value="COMPLETED">Comprehensive coverage</option>
                </select></div>
            </div>





            <input type="hidden" name="command" value="register_client">
            <button class="btn btn-light submit-button" type="submit" id="butt">
                Checkout</button>
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
<script src="${pageContext.request.contextPath}/js/date_range.js"></script>
</body>
</html>