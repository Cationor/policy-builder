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
    <link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200;0,300;0,400;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Dark.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Pretty-Registration-Form.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
</head>

<body id="page-top" style="background-color: rgb(56,55,57);">
<c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/>
<header class="masthead">
    <div class="intro-body">
    <div class="row register-form" id="login_form">
    <div class="col-md-8 offset-md-2">
        <form action="CarBook" method="post" class="shadow-lg custom-form" style="font-family: Nunito">
            <label id="labelPage">Договор страхования имущества</label>

            <div class="form-row form-group">

${requestScope.policyList.firstName} ${requestScope.policyList.secondName}, именующийся в дальнейшем как "страховщик",
    заключил договор,в дальнейшем "Договор", с компанией EIS|GROUP о нижеследующем
    <label id="labelPage">Предмет договора</label>
    Согласно настоящему договору Страховщик обязается при наступлении одного из обусловленных в договоре
    ${requestScope.policyList.termOfValidity} страховых случаев, повлекших утрату или повреждение имущества
    типа ${requestScope.policyList.registeredObject}, именуемого далее "Застрахованное имущество", выплатить
    возмещение в размере "${requestScope.policyList.sumInsured} ${requestScope.policyList.contractCurrency}".
    Страховка работает только территории ${requestScope.policyList.insuranceCoverageArea} и до
    ${requestScope.policyList.termOfValidity}

                Дата                   Роспись





            </div>
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
</body>

</html>