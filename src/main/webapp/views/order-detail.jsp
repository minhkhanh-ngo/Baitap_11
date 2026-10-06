<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="st" value="${order.statusEnum}"/>

<div class="d-flex flex-wrap justify-content-between align-items-center mb-3 gap-2">
    <h2 class="mb-0">Đơn hàng #${order.orderId}</h2>
    <c:choose>
        <c:when test="${not empty st}"><span class="badge text-bg-${st.badge} fs-5"><c:out value="${st.label}"/></span></c:when>
        <c:otherwise><span class="badge text-bg-secondary fs-5"><c:out value="${order.status}"/></span></c:otherwise>
    </c:choose>
</div>

<c:choose>
    <c:when test="${not empty st and st.abnormal}">
        <div class="alert alert-danger">
            <c:choose>
                <c:when test="${st.code == 'cancelled'}">Đơn hàng này đã bị hủy.</c:when>
                <c:otherwise>Đơn hàng này đã được hoàn trả.</c:otherwise>
            </c:choose>
        </div>
    </c:when>
    <c:when test="${not empty st}">
        <div class="row text-center g-0 mb-4">
            <c:forEach var="s" items="${statuses}">
                <c:if test="${s.step > 0}">
                    <div class="col">
                        <div class="rounded-circle mx-auto d-flex align-items-center justify-content-center fw-bold
                                    ${s.step <= st.step ? 'bg-success text-white' : 'bg-light text-muted border'}"
                             style="width:40px;height:40px;">
                            <c:choose>
                                <c:when test="${s.step < st.step}">&#10003;</c:when>
                                <c:otherwise>${s.step}</c:otherwise>
                            </c:choose>
                        </div>
                        <div class="small mt-1 ${s.step == st.step ? 'fw-bold text-success' : 'text-muted'}">
                            <c:out value="${s.label}"/>
                        </div>
                    </div>
                </c:if>
            </c:forEach>
        </div>
    </c:when>
    <c:otherwise>
        <div class="alert alert-secondary">Trạng thái hiện tại: <c:out value="${order.status}"/></div>
    </c:otherwise>
</c:choose>

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
                <td colspan="3" class="text-end fw-bold">Tổng tiền:</td>
                <td class="text-end fw-bold text-danger"><fmt:formatNumber value="${order.totalAmount}" pattern="#,##0"/> đ</td>
            </tr>
            </tfoot>
        </table>
    </div>
</div>

<div class="text-center mt-3">
    <a class="btn btn-outline-secondary" href="${ctx}/orders">&larr; Quay lại lịch sử đặt hàng</a>
</div>
