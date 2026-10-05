<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <title><sitemesh:write property="title"/></title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <sitemesh:write property="head"/>
</head>
<body class="bg-light d-flex flex-column min-vh-100">

<div class="container my-auto flex-grow-1 d-flex align-items-center justify-content-center py-5">
  <div class="w-100" style="max-width: 650px;">
    <sitemesh:write property="body"/>
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