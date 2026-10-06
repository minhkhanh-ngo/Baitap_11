<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>

<div class="text-center mb-4">
    <h2 class="text-success">Đặt hàng thành công!</h2>
    <p class="text-muted">Cảm ơn bạn. Mã đơn hàng của bạn là <strong>#${order.orderId}</strong>.</p>
</div>

<div class="row g-4">
    <div class="col-md-5">
        <h5>Thông tin nhận hàng</h5>
        <p class="mb-1"><strong>Người nhận:</strong> <c:out value="${order.receiverName}"/></p>
        <p class="mb-1"><strong>Điện thoại:</strong> <c:out value="${order.phone}"/></p>
        <p class="mb-1"><strong>Địa chỉ:</strong> <c:out value="${order.address}"/></p>
        <c:if test="${not empty order.note}">
            <p class="mb-1"><strong>Ghi chú:</strong> <c:out value="${order.note}"/></p>
        </c:if>
        <p class="mb-1"><strong>Ngày đặt:</strong> <fmt:formatDate value="${order.createdDate}" pattern="dd/MM/yyyy HH:mm"/></p>
        <p class="mb-1"><strong>Thanh toán:</strong> Thanh toán khi nhận hàng (${order.paymentMethod})</p>
        <p class="mb-1"><strong>Trạng thái:</strong> <span class="badge text-bg-${empty order.statusEnum ? 'secondary' : order.statusEnum.badge}"><c:out value="${order.status}"/></span></p>
    </div>
    <div class="col-md-7">
        <h5>Chi tiết đơn hàng</h5>
        <table class="table">
            <thead class="table-light">
            <tr><th>Sản phẩm</th><th class="text-center">SL</th><th class="text-end">Đơn giá</th><th class="text-end">Thành tiền</th></tr>
            </thead>
            <tbody>
            <c:forEach var="d" items="${order.details}">
                <tr>
                    <td><c:out value="${d.title}"/></td>
                    <td class="text-center">${d.quantity}</td>
                    <td class="text-end"><fmt:formatNumber value="${d.unitPrice}" pattern="#,##0"/> đ</td>
                    <td class="text-end"><fmt:formatNumber value="${d.subtotal}" pattern="#,##0"/> đ</td>
                </tr>
            </c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td colspan="3" class="text-end fw-bold">Tổng thanh toán khi nhận hàng:</td>
                <td class="text-end fw-bold text-danger"><fmt:formatNumber value="${order.totalAmount}" pattern="#,##0"/> đ</td>
            </tr>
            </tfoot>
        </table>
    </div>
</div>

<div class="text-center mt-3">
    <a class="btn btn-outline-primary me-2" href="${pageContext.request.contextPath}/orders/detail?id=${order.orderId}">Xem đơn hàng</a>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/user/videos">Tiếp tục mua sắm</a>
</div>
