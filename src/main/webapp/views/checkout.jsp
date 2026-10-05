<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>

<h2 class="mb-4">Thanh toán đơn hàng</h2>

<c:if test="${not empty error}">
    <div class="alert alert-danger"><c:out value="${error}"/></div>
</c:if>

<div class="row g-4">
    <div class="col-lg-7">
        <form action="${pageContext.request.contextPath}/checkout" method="post">
            <input type="hidden" name="token" value="<c:out value='${token}'/>">

            <h5 class="mb-3">Thông tin giao hàng</h5>
            <div class="mb-3">
                <label class="form-label fw-semibold" for="receiverName">Họ tên người nhận *</label>
                <input type="text" class="form-control" id="receiverName" name="receiverName" maxlength="100"
                       value="<c:out value='${receiverName}'/>" required>
            </div>
            <div class="mb-3">
                <label class="form-label fw-semibold" for="phone">Số điện thoại *</label>
                <input type="tel" class="form-control" id="phone" name="phone" maxlength="12"
                       pattern="(0|\+84)[0-9]{9}" title="VD: 0912345678"
                       value="<c:out value='${phone}'/>" required>
            </div>
            <div class="mb-3">
                <label class="form-label fw-semibold" for="address">Địa chỉ giao hàng *</label>
                <textarea class="form-control" id="address" name="address" rows="2" maxlength="300" required><c:out value="${address}"/></textarea>
            </div>
            <div class="mb-4">
                <label class="form-label fw-semibold" for="note">Ghi chú</label>
                <textarea class="form-control" id="note" name="note" rows="2" maxlength="500"><c:out value="${note}"/></textarea>
            </div>

            <h5 class="mb-3">Phương thức thanh toán</h5>
            <div class="form-check border rounded p-3 mb-4 ps-5">
                <input class="form-check-input" type="radio" id="cod" checked disabled>
                <label class="form-check-label" for="cod">
                    <strong>Thanh toán khi nhận hàng (COD)</strong><br>
                    <span class="text-muted small">Bạn thanh toán bằng tiền mặt cho nhân viên giao hàng khi nhận sản phẩm.</span>
                </label>
            </div>

            <div class="d-flex justify-content-between">
                <a class="btn btn-outline-secondary" href="${pageContext.request.contextPath}/cart">← Quay lại giỏ hàng</a>
                <button type="submit" class="btn btn-success btn-lg">Đặt hàng</button>
            </div>
        </form>
    </div>

    <div class="col-lg-5">
        <div class="card bg-light border-0">
            <div class="card-body">
                <h5 class="mb-3">Đơn hàng của bạn</h5>
                <c:forEach var="item" items="${sessionScope.cart.items}">
                    <div class="d-flex justify-content-between mb-2">
                        <span><c:out value="${item.title}"/> <span class="text-muted">× ${item.quantity}</span></span>
                        <span><fmt:formatNumber value="${item.subtotal}" pattern="#,##0"/> đ</span>
                    </div>
                </c:forEach>
                <hr>
                <div class="d-flex justify-content-between fs-5 fw-bold">
                    <span>Tổng cộng</span>
                    <span class="text-danger"><fmt:formatNumber value="${sessionScope.cart.totalAmount}" pattern="#,##0"/> đ</span>
                </div>
            </div>
        </div>
    </div>
</div>
