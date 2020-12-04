<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.contentpage"/>
<head>
</head>
<body>
<script src="${pageContext.request.contextPath}/js/button_block.js"></script>
</body>
<footer style="background-color: #688181">
    <div class="container text-center">
        <p><fmt:message key="footer.copyright"/></p>
    </div>
</footer>