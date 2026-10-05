<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title><sitemesh:write property="title"/></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <sitemesh:write property="head"/>
</head>
<body class="bg-light d-flex flex-column min-vh-100">
<nav class="navbar navbar-expand-lg navbar-dark bg-danger shadow-sm">
    <div class="container px-4">
        <a class="navbar-brand fw-bold" href="${pageContext.request.contextPath}/admin/home">⚙️ ADMIN PORTAL</a>
        <div class="navbar-nav ms-auto align-items-center">
            <a class="nav-link px-3" href="${pageContext.request.contextPath}/admin/users">Quản Lý User</a>
            <a class="nav-link text-white px-3 fw-bold" href="${pageContext.request.contextPath}/home">Về Trang Chủ User</a>
            <a class="nav-link text-warning px-3 fw-bold" href="${pageContext.request.contextPath}/logout">Đăng Xuất</a>
        </div>
    </div>
</nav>

<div class="container my-4 flex-grow-1">
    <div class="card shadow-sm border-0 p-4 bg-white rounded-4 w-100">
        <sitemesh:write property="body"/>
    </div>
</div>

<footer class="bg-dark text-white text-center py-3 mt-auto shadow-sm">
    <div class="container">
        <small>Admin Panel | Ngô Minh Khánh - 24110248</small>
    </div>
</footer>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>