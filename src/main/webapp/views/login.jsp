<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div style="max-width: 400px; margin: 40px auto; padding: 30px; background: #fff; border: 1px solid #ddd; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.05);">
    <h2 style="text-align: center; margin-bottom: 25px; color: #333;">Đăng Nhập Hệ Thống</h2>

    <c:if test="${param.msg == 'success'}">
        <p style="color: #28a745; font-weight: bold; text-align: center; margin-bottom: 15px;">Xác nhận OTP thành công! Vui lòng đăng nhập.</p>
    </c:if>

    <c:if test="${not empty error}">
        <p style="color: #dc3545; font-weight: bold; text-align: center; margin-bottom: 15px;">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/login" method="post">
        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #555;">Username:</label>
            <input type="text" name="username" style="width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; font-size: 14px;" required>
        </div>
        <div style="margin-bottom: 20px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #555;">Password:</label>
            <input type="password" name="password" style="width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; font-size: 14px;" required>
        </div>
        <button type="submit" style="width: 100%; padding: 12px; background: #0056b3; color: white; border: none; border-radius: 4px; font-size: 16px; font-weight: bold; cursor: pointer;">Đăng Nhập</button>
    </form>

    <div style="text-align: center; margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/register" style="color: #0056b3; text-decoration: none; font-size: 14px;">Chưa có tài khoản? Đăng ký ngay</a>
    </div>
</div>