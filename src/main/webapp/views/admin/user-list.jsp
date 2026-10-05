<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="padding: 10px;">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
        <h2 style="margin: 0; color: #333;">Quản lý Tài khoản Người dùng</h2>
        <a href="${pageContext.request.contextPath}/admin/users/edit" style="background: #28a745; color: white; padding: 10px 18px; text-decoration: none; border-radius: 4px; font-weight: bold; font-size: 14px;">+ Thêm User Mới</a>
    </div>

    <table style="width: 100%; border-collapse: collapse; background: white; box-shadow: 0 1px 3px rgba(0,0,0,0.1); border-radius: 6px; overflow: hidden;">
        <thead>
        <tr style="background: #343a40; color: white; text-align: left;">
            <th style="padding: 12px 15px; border-bottom: 2px solid #dee2e6;">Username</th>
            <th style="padding: 12px 15px; border-bottom: 2px solid #dee2e6;">Họ tên</th>
            <th style="padding: 12px 15px; border-bottom: 2px solid #dee2e6;">Email</th>
            <th style="padding: 12px 15px; border-bottom: 2px solid #dee2e6;">Số điện thoại</th>
            <th style="padding: 12px 15px; border-bottom: 2px solid #dee2e6; text-align: center;">Vai trò</th>
            <th style="padding: 12px 15px; border-bottom: 2px solid #dee2e6; text-align: center;">Trạng thái</th>
            <th style="padding: 12px 15px; border-bottom: 2px solid #dee2e6; text-align: center;">Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="u" items="${users}">
            <tr style="border-bottom: 1px solid #dee2e6;">
                <td style="padding: 12px 15px; font-weight: bold; color: #333;">${u.username}</td>
                <td style="padding: 12px 15px; color: #555;">${u.fullname}</td>
                <td style="padding: 12px 15px; color: #666;">${u.email}</td>
                <td style="padding: 12px 15px; color: #555;">${u.phone}</td>
                <td style="padding: 12px 15px; text-align: center;">
                    <c:choose>
                        <c:when test="${u.admin}">
                            <span style="background: #dc3545; color: white; padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold;">Admin</span>
                        </c:when>
                        <c:otherwise>
                            <span style="background: #6c757d; color: white; padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold;">User</span>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td style="padding: 12px 15px; text-align: center;">
                    <span style="background: #28a745; color: white; padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold;">Hoạt động</span>
                </td>
                <td style="padding: 12px 15px; text-align: center;">
                    <a href="${pageContext.request.contextPath}/admin/users/edit?id=${u.username}" style="color: #007bff; text-decoration: none; margin-right: 12px; font-weight: bold;">Sửa</a>
                    <a href="${pageContext.request.contextPath}/admin/users/delete?id=${u.username}" style="color: #dc3545; text-decoration: none; font-weight: bold;" onclick="return confirm('Bạn có chắc muốn xóa?');">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div style="margin-top: 20px;">
        <strong>Trang: </strong>
        <c:forEach begin="1" end="${totalPages}" var="i">
            <a href="?page=${i}" style="padding: 5px 10px; border: 1px solid #ccc; margin-right: 5px; text-decoration: none; border-radius: 4px;
               ${currentPage == i ? 'background-color: #007bff; color: white;' : 'color: black; background: #fff;'}">
                    ${i}
            </a>
        </c:forEach>
    </div>
</div>