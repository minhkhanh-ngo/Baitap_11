<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="max-width: 500px; margin: 20px auto; background: #fff; border: 1px solid #ddd; border-radius: 8px; padding: 30px; box-shadow: 0 4px 8px rgba(0,0,0,0.05);">
    <h2 style="margin-top: 0; margin-bottom: 25px; color: #333; text-align: center;">${not empty user ? 'Cập nhật Người dùng' : 'Thêm mới Người dùng'}</h2>
    <form action="${pageContext.request.contextPath}/admin/users/save" method="post">

        <input type="hidden" name="isEdit" value="${not empty user ? 'true' : 'false'}">

        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #555;">Username (ID):</label>
            <input type="text" name="username" value="${user.username}" ${not empty user ? 'readonly' : ''} required style="width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; font-size: 14px; background: ${not empty user ? '#e9ecef' : '#fff'};">
        </div>

        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #555;">Password:</label>
            <input type="text" name="password" value="${user.password}" required style="width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; font-size: 14px;">
        </div>

        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #555;">Họ và Tên:</label>
            <input type="text" name="fullname" value="${user.fullname}" required style="width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; font-size: 14px;">
        </div>

        <div style="margin-bottom: 15px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #555;">Email:</label>
            <input type="email" name="email" value="${user.email}" required style="width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; font-size: 14px;">
        </div>

        <div style="margin-bottom: 20px;">
            <label style="display: block; margin-bottom: 5px; font-weight: bold; color: #555;">Số điện thoại:</label>
            <input type="text" name="phone" value="${user.phone}" style="width: 100%; padding: 10px; box-sizing: border-box; border: 1px solid #ccc; border-radius: 4px; font-size: 14px;">
        </div>

        <div style="margin-bottom: 12px; display: flex; align-items: center; gap: 8px;">
            <input type="checkbox" name="admin" id="adminCheck" ${user.admin ? 'checked' : ''} style="width: 16px; height: 16px;">
            <label for="adminCheck" style="font-weight: 500; color: #333; cursor: pointer;">Là Quản trị viên (Admin)</label>
        </div>

        <div style="margin-bottom: 25px; display: flex; align-items: center; gap: 8px;">
            <input type="checkbox" name="active" id="activeCheck" ${user.active || empty user ? 'checked' : ''} style="width: 16px; height: 16px;">
            <label for="activeCheck" style="font-weight: 500; color: #333; cursor: pointer;">Kích hoạt tài khoản</label>
        </div>

        <div style="display: flex; gap: 10px;">
            <button type="submit" style="flex: 1; padding: 12px; background: #007bff; color: white; border: none; border-radius: 4px; font-size: 15px; font-weight: bold; cursor: pointer;">Lưu Dữ Liệu</button>
            <a href="${pageContext.request.contextPath}/admin/users" style="flex: 1; padding: 12px; background: #6c757d; color: white; text-align: center; text-decoration: none; border-radius: 4px; font-size: 15px; font-weight: bold;">Quay lại</a>
        </div>
    </form>
</div>