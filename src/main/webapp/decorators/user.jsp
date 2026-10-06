<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title><sitemesh:write property="title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <sitemesh:write property="head"/>
</head>
<body class="bg-light d-flex flex-column min-vh-100">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow-sm">
    <div class="container px-4">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/home">IOTSTAR APP</a>

        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNav">
            <div class="navbar-nav ms-auto align-items-center">
                <a class="nav-link px-3" href="${pageContext.request.contextPath}/home">Trang Chủ</a>
                <a class="nav-link px-3" href="${pageContext.request.contextPath}/user/videos">Sản Phẩm</a>

                <c:choose>
                    <c:when test="${not empty sessionScope.loggedInUser}">
                        <a class="nav-link px-3" href="${pageContext.request.contextPath}/cart">
                            Giỏ hàng
                            <span class="badge rounded-pill bg-danger">${empty sessionScope.cart ? 0 : sessionScope.cart.totalQuantity}</span>
                        </a>
                        <a class="nav-link px-3" href="${pageContext.request.contextPath}/orders">Đơn hàng</a>
                        <!-- Chỉ Admin mới thấy nút Trang Quản Trị -->
                        <c:if test="${sessionScope.loggedInUser.admin == true}">
                            <a class="nav-link text-info px-3 fw-bold" href="${pageContext.request.contextPath}/admin/home">Trang Quản Trị</a>
                        </c:if>
                        <a class="nav-link text-danger px-3 fw-bold" href="${pageContext.request.contextPath}/logout">Đăng Xuất</a>
                    </c:when>
                    <c:otherwise>
                        <a class="nav-link text-success px-3 fw-bold" href="${pageContext.request.contextPath}/login">Đăng Nhập</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</nav>

<!-- NỘI DUNG CHÍNH (Đã được căn giữa màn hình, không bị lệch phải) -->
<div class="container my-4 flex-grow-1 d-flex justify-content-center">
    <div class="w-100" style="max-width: 1200px;">
        <div class="card shadow-sm border-0 p-4 bg-white rounded-4">
            <c:if test="${not empty sessionScope.flashSuccess}">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <c:out value="${sessionScope.flashSuccess}"/>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="flashSuccess" scope="session"/>
            </c:if>
            <c:if test="${not empty sessionScope.flashError}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <c:out value="${sessionScope.flashError}"/>
                    <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                </div>
                <c:remove var="flashError" scope="session"/>
            </c:if>
            <sitemesh:write property="body"/>
        </div>
    </div>
</div>

<footer class="bg-secondary text-white text-center py-3 mt-auto shadow-sm">
    <div class="container">
        <small>Họ tên: Ngô Minh Khánh | MSSV: 24110248 | Mã đề: 04</small>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>