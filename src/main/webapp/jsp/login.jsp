<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.contentpage"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="title.login"/></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Dark.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Pretty-Registration-Form.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
</head>

<body id="page-top" style="background-color: rgb(56,55,57);">
<header class="masthead">
    <div class="intro-body">
    <div class="col-md-8 offset-md-3">

            <div class="col-8">
                <label id="labelPage">Договор страхования имущества</label>

            ${requestScope.policyList.firstName} ${requestScope.policyList.secondName}, именующийся
    в дальнейшем как "страховщик", заключил договор,в дальнейшем "Договор", с компанией EIS|GROUP о нижеследующем
                <label id="labelPage">Предмет договора</label>
    Согласно настоящему договору Страховщик обязается при наступлении одного из обусловленных в договоре
    ${requestScope.policyList.termOfValidity} страховых случаев, повлекших утрату или повреждение имущества
    типа ${requestScope.policyList.registeredObject}, именуемого далее "Застрахованное имущество", выплатить
    возмещение в размере "${requestScope.policyList.sumInsured} ${requestScope.policyList.contractCurrency}".
    Страховка работает только территории ${requestScope.policyList.insuranceCoverageArea} и до
    ${requestScope.policyList.termOfValidity}
                <label id="labelPage">JSON</label>
            ${requestScope.gsonKey}
            </div>
<%--        <div class="col-8">--%>
<%--            ${requestScope.gsonKey}--%>
<%--        </div>--%>


    </div>
    </div>
</header>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-easing/1.4.1/jquery.easing.min.js"></script>
<script src="${pageContext.request.contextPath}/js/grayscale.js"></script>
</body>

</html>