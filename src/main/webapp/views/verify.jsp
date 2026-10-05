<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="max-width: 400px; margin: 40px auto; padding: 30px; background: #fff; border: 1px solid #ddd; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.05); text-align: center;">
    <h2 style="margin-bottom: 15px; color: #333;">Xác nhận OTP</h2>

    <c:if test="${not empty error}">
        <p style="color: #dc3545; font-weight: bold; margin-bottom: 15px;">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/verify" method="post">
        <p style="color: #666; font-size: 14px; margin-bottom: 20px;">Vui lòng kiểm tra Email (hoặc Console của IDE) để lấy mã OTP.</p>
        <div style="margin-bottom: 20px; text-align: left;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #555;">Mã OTP:</label>
            <input type="text" name="otp" style="width: 100%; padding: 12px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; font-size: 18px; text-align: center; letter-spacing: 2px;" required>
        </div>
        <button type="submit" style="width: 100%; padding: 12px; background: #0056b3; color: white; border: none; border-radius: 4px; font-size: 16px; font-weight: bold; cursor: pointer;">Xác nhận</button>
    </form>
</div>