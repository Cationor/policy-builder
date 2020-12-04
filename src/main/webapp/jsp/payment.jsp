<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.contentpage"/>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="title.payment"/></title>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200;0,300;0,400;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,600;1,700;1,800;1,900&display=swap"
          rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Dark.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Pretty-Registration-Form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
</head>

<body>
<c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/>
<header class="masthead" id="mastHeadCars">
    <div class="intro-body">
        <div class="container py-5">
            <div class="row register-form">
                <div class="col-lg-7 mx-auto" id="paymentPage">
                    <div class="rounded-lg p-5">
                        <ul role="tablist" class="nav nav-fill">
                            <li class="nav-item">
                                <label id="labelPage">
                                    <i class="fa fa-credit-card"></i>
                                    <fmt:message key="label.payment"/>
                                </label>
                            </li>
                        </ul>
                        <div class="tab-content">
                            <div id="nav-tab-card" class="tab-pane fade show active">
                                <c:if test="${requestScope.paymentFailed}">
                                    <p class="alert alert-danger"><fmt:message key="payment.label.payment_error"/></p>
                                </c:if>
                                <form action="CarBook" method="post" role="form" class="custom-form">
                                    <div class="form-group">
                                        <label for="username"><fmt:message key="payment.label.card_holder"/></label>
                                        <input type="text" id="username" name="card_holder"
                                               placeholder="<fmt:message key="payment.placeholder.full_name"/>" required
                                               pattern="[a-zA-Z]{1,20} [a-zA-Z]{1,20}" class="form-control"
                                               oninvalid="this.setCustomValidity('<fmt:message key="payment.card_holder.validation"/>')"
                                               onchange="this.setCustomValidity('')">
                                    </div>
                                    <div class="form-group">
                                        <label for="cardNumber"><fmt:message key="payment.label.card_number"/></label>
                                        <div class="input-group">
                                            <input type="text" id="cardNumber" name="card_number" placeholder="<fmt:message key="payment.placeholder.card_number"/>"
                                                   class="form-control" pattern="([0-9]{4}\s?){4}" required
                                                   oninvalid="this.setCustomValidity('<fmt:message key="payment.card_number.validation"/>')"
                                                   onchange="this.setCustomValidity('')">
                                            <div class="input-group-append">
                                            <span class="input-group-text text-muted">
                                                <i class="fa fa-cc-visa mx-1"></i>
                                                <i class="fa fa-cc-amex mx-1"></i>
                                                <i class="fa fa-cc-mastercard mx-1"></i>
                                            </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-8">
                                            <div class="form-group">
                                                <label><span class="hidden-xs"><fmt:message key="payment.label.expiration"/></span></label>
                                                <div class="input-group">
                                                    <input type="number" placeholder="<fmt:message key="payment.placeholder.month"/>"
                                                           name="card_exp_month" class="form-control"
                                                           min="1" max="12" required
                                                           oninvalid="this.setCustomValidity('<fmt:message key="payment.month.validation"/>')"
                                                           onchange="this.setCustomValidity('')">
                                                    <input type="number" placeholder="<fmt:message key="payment.placeholder.year"/>"
                                                           name="card_exp_year" class="form-control"
                                                           min="20" max="29" required
                                                           oninvalid="this.setCustomValidity('<fmt:message key="payment.year.validation"/>')"
                                                           onchange="this.setCustomValidity('')">
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-sm-4">
                                            <div class="form-group mb-4">
                                                <label data-toggle="tooltip"><fmt:message key="payment.label.cvv"/></label>
                                                <input type="text" name="card_cvv" placeholder="<fmt:message key="payment.placeholder.cvv"/>"
                                                       pattern="[0-9]{3}" min="1" required class="form-control"
                                                       oninvalid="this.setCustomValidity('<fmt:message key="payment.cvv.validation"/>')"
                                                       onchange="this.setCustomValidity('')">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="input">
                                        <input type="hidden" name="command" value="make_order_payment">
                                        <button type="submit" class="submit-button btn btn-block shadow-sm" id="butt">
                                            <fmt:message key="payment.submit.pay"/> ${sessionScope.payableOrder.amount}$
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</header>
<c:import url="${pageContext.request.contextPath}/jsp/fragment/footer.jsp"/>

<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/Bootstrap-4---Payment-Form.js"></script>
<script src="${pageContext.request.contextPath}/js/grayscale.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

</body>

</html>