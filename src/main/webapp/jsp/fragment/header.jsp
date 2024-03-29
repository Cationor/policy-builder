<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.contentpage"/>

<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<nav class="navbar navbar-light navbar-expand-md navbar navbar-expand-lg fixed-top" id="mainNav">
    <div class="container">
        <a class="navbar-brand shadow-none js-scroll-trigger" id="brandLogo"
                              href="CarBook?command=move_home_page"><fmt:message key="title.brand_name"/></a>
        <button data-toggle="collapse" class="navbar-toggler navbar-toggler-right" data-target="#navbarResponsive"
                type="button" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation"
                value="Menu"><i class="fa fa-bars"></i></button>
        <div class="collapse navbar-collapse text-success" id="navbarResponsive">
            <ul class="nav navbar-nav ml-auto">
                <li class="nav-item nav-link js-scroll-trigger" role="presentation">
                    <a class="nav-link js-scroll-trigger" href="CarBook?command=move_home_page">
                        <fmt:message key="label.home"/>
                    </a>
                </li>
                <c:if test="${sessionScope.user.role.name()=='CLIENT'}">
                    <li class="nav-item nav-link js-scroll-trigger" role="presentation">
                        <a class="nav-link js-scroll-trigger" href="CarBook?command=move_orders_page">
                            <fmt:message key="label.orders"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user.role.name()=='ADMIN'}">
                    <li class="nav-item nav-link js-scroll-trigger" role="presentation">
                        <a class="nav-link js-scroll-trigger" href="CarBook?command=move_orders_page">
                            <fmt:message key="label.orders"/>
                        </a>
                    </li>
                    <li class="nav-item nav-link js-scroll-trigger" role="presentation">
                        <a class="nav-link js-scroll-trigger" href="CarBook?command=move_users_page">
                            <fmt:message key="label.users"/>
                        </a>
                    </li>
                </c:if>
                <li class="nav-item nav-link js-scroll-trigger" role="presentation">
                    <a class="nav-link js-scroll-trigger"
                       href="CarBook?command=move_register_page">
                        <fmt:message key="label.login"/>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</nav>