<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="prop.contentpage"/>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title><fmt:message key="title.create_car"/></title>
    <link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200;0,300;0,400;0,600;0,700;0,800;0,900;1,200;1,300;1,400;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Login-Form-Dark.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/Pretty-Registration-Form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/img/favicon.ico" type="image/x-icon">
</head>

<body id="page-top" style="background-color: rgb(56,55,57);">
<c:import url="${pageContext.request.contextPath}/jsp/fragment/header.jsp"/>
<header class="masthead">
    <div class="intro-body">
        <div class="row register-form">
            <div class="shadow-lg custom-form" id="createCarsForm">
                <table id="addCarTable">
                    <form action="boot_image_controller" enctype="multipart/form-data" method="post">
                    <tbody>
                    <tr>
                        <td><fmt:message key="create_car.image_icon"/></td>
                        <td colspan="3">
                            <div class="form-group load-file">
                                <input type="file" name="file" id="file1">
                            </div>
                        </td>
                        <td>
                            <input type="hidden" name="image_type" value="exterior_small">
                            <button class="submit-button" type="submit">
                                <fmt:message key="create_car.upload_image"/>
                        </td>
                        <td colspan="2">
                            <c:if test="${not empty sessionScope.exterior_small}">
                                <div class="alert alert-success" role="alert">
                                    <fmt:message key="create_car.successfully_uploaded"/>
                                </div>
                            </c:if>
                            <c:if test="${sessionScope.icon_image==''}">
                                <div class="alert alert-danger" role="alert">
                                    <fmt:message key="create_car.unsuccessfully_uploaded"/>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                    </tbody>
                    </form>
                    <form action="boot_image_controller" enctype="multipart/form-data" method="post">
                    <tbody>
                    <tr>
                        <td><fmt:message key="create_car.image_exterior"/></td>
                        <td colspan="3">
                            <div class="form-group">
                                <input type="file" name="file">
                            </div>
                        </td>
                        <td>
                            <input type="hidden" name="image_type" value="exterior">
                            <button class="submit-button" type="submit">
                                <fmt:message key="create_car.upload_image"/>
                        </td>
                        <td colspan="2">
                            <c:if test="${not empty sessionScope.exterior}">
                                <div class="alert alert-success" role="alert">
                                    <fmt:message key="create_car.successfully_uploaded"/>
                                </div>
                            </c:if>
                            <c:if test="${sessionScope.exterior_image==''}">
                                <div class="alert alert-danger" role="alert">
                                    <fmt:message key="create_car.unsuccessfully_uploaded"/>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                    </tbody>
                    </form>
                    <form action="boot_image_controller" enctype="multipart/form-data" method="post">
                    <tbody>
                    <tr>
                        <td><fmt:message key="create_car.image_interior"/></td>
                        <td colspan="3">
                            <div class="form-group">
                                <input type="file" name="file">
                            </div>
                        </td>
                        <td>
                            <input type="hidden" name="image_type" value="interior">
                            <button class="submit-button" type="submit">
                                <fmt:message key="create_car.upload_image"/>
                        </td>
                        <td colspan="2">
                            <c:if test="${not empty sessionScope.interior}">
                                <div class="alert alert-success" role="alert">
                                    <fmt:message key="create_car.successfully_uploaded"/>
                                </div>
                            </c:if>
                            <c:if test="${sessionScope.interior_image==''}">
                                <div class="alert alert-danger" role="alert">
                                    <fmt:message key="create_car.unsuccessfully_uploaded"/>
                                </div>
                            </c:if>
                        </td>
                    </tr>
                    </tbody>
                    </form>
                    <form action="CarBook" method="post" id="createCar" style="background-color: rgba(0,0,0,0.08); font-family: Nunito">
                    <tbody>
                    <tr>
                        <td><fmt:message key="create_car.model"/> &#8432;</td>
                        <td>
                            <input class="form-control" name="model" type="text" required
                                   pattern="^[a-zA-Z0-9 -]{2,100}$" value="${carParameters.get("model")}"
                                   oninvalid="this.setCustomValidity('<fmt:message key="create_car.model.validation"/>')"
                                   onchange="this.setCustomValidity('')">
                        </td>
                        <td><fmt:message key="create_car.type"/></td>
                        <td>
                            <select size="1" class="form-control" name="car_type">
                                <option selected value="SUV"><fmt:message key="create_car.car_type.suv"/></option>
                                <option value="SEDAN"><fmt:message key="create_car.car_type.sedan"/></option>
                                <option value="MINIVAN"><fmt:message key="create_car.car_type.minivan"/></option>
                            </select>
                        </td>
                        <td><fmt:message key="create_car.rent_cost"/> &#8432;</td>
                        <td>
                            <input class="form-control" name="rent_cost" type="number" required
                                   minlength="2" maxlength="3" min="10" max="200" value="${carParameters.get("rent_cost")}"
                                   oninvalid="this.setCustomValidity('<fmt:message key="create_car.rent_cost.validation"/>')"
                                   onchange="this.setCustomValidity('')">
                        </td>
                        <td><fmt:message key="create_car.is_available"/></td>
                        <td>
                            <select size="1" class="form-control" name="is_available">
                                <option selected value="true">True</option>
                                <option value="false">False</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><fmt:message key="create_car.number_seats"/> &#8432;</td>
                        <td>
                            <input class="form-control" name="number_seats" type="number" required
                                   minlength="1" maxlength="2" min="1" max="50" value="${carParameters.get("number_seats")}"
                                   oninvalid="this.setCustomValidity('<fmt:message key="create_car.number_seats.validation"/>')"
                                   onchange="this.setCustomValidity('')">

                        </td>
                        <td><fmt:message key="create_car.fuel_type"/></td>
                        <td>
                            <select size="1" class="form-control" name="fuel_type">
                                <option selected value="DIESEL"><fmt:message key="create_car.fuel_type.diesel"/></option>
                                <option value="PETROL"><fmt:message key="create_car.fuel_type.petrol"/></option>
                            </select>
                        </td>
                        <td><fmt:message key="create_car.fuel_consumption"/> &#8432;</td>
                        <td>
                            <input class="form-control" name="fuel_consumption" type="number" required
                                   minlength="1" maxlength="2" min="1" max="50" value="${carParameters.get("fuel_consumption")}"
                                   oninvalid="this.setCustomValidity('<fmt:message key="create_car.fuel_consumption.validation"/>')"
                                   onchange="this.setCustomValidity('')">
                        </td>
                        <td align="center" colspan="2">
                            <input type="hidden" name="command" value="add_car">
                            <button class="submit-button" type="submit">
                                <fmt:message key="create_car.create"/>
                        </td>
                    </tr>
                    </tbody>
                    </form>
                </table>
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