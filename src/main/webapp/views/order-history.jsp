<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h2 class="mb-3">Lịch sử đặt hàng</h2>

<ul class="nav nav-pills flex-nowrap overflow-auto pb-2 mb-3 border-bottom">
    <li class="nav-item">
        <a class="nav-link text-nowrap ${empty currentStatus ? 'active' : ''}" href="${ctx}/orders">
            Tất cả <span class="badge rounded-pill text-bg-light ms-1">${totalCount}</span>
        </a>
    </li>
    <c:forEach var="s" items="${statuses}">
        <li class="nav-item">
            <a class="nav-link text-nowrap ${currentStatus == s ? 'active' : ''}" href="${ctx}/orders?status=${s.code}">
                <c:out value="${s.label}"/>
                <span class="badge rounded-pill text-bg-${currentStatus == s ? 'light' : s.badge} ms-1">${counts[s.code]}</span>
            </a>
        </li>
    </c:forEach>
</ul>

<c:choose>
    <c:when test="${empty orders}">
        <div class="text-center py-5">
            <p class="text-muted fs-5 mb-3">
                <c:choose>
                    <c:when test="${empty currentStatus}">Bạn chưa có đơn hàng nào.</c:when>
                    <c:otherwise>Không có đơn hàng nào ở trạng thái "<c:out value="${currentStatus.label}"/>".</c:otherwise>
                </c:choose>
            </p>
            <a class="btn btn-primary" href="${ctx}/user/videos">Tiếp tục mua sắm</a>
        </div>
    </c:when>
    <c:otherwise>
        <c:forEach var="o" items="${orders}">
            <div class="card mb-3 shadow-sm">
                <div class="card-header bg-white d-flex flex-wrap justify-content-between align-items-center gap-2">
                    <div>
                        <strong>Đơn hàng #${o.orderId}</strong>
                        <span class="text-muted small ms-2">
                            <fmt:formatDate value="${o.createdDate}" pattern="dd/MM/yyyy HH:mm"/>
                        </span>
                    </div>
                    <c:choose>
                        <c:when test="${not empty o.statusEnum}">
                            <span class="badge text-bg-${o.statusEnum.badge} fs-6"><c:out value="${o.statusEnum.label}"/></span>
                        </c:when>
                        <c:otherwise>
                            <span class="badge text-bg-secondary fs-6"><c:out value="${o.status}"/></span>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="card-body py-2">
                    <c:forEach var="d" items="${o.details}">
                        <div class="d-flex justify-content-between py-1">
                            <span><c:out value="${d.title}"/> <span class="text-muted">x${d.quantity}</span></span>
                            <span class="text-muted"><fmt:formatNumber value="${d.subtotal}" pattern="#,##0"/> đ</span>
                        </div>
                    </c:forEach>
                </div>
                <div class="card-footer bg-white d-flex flex-wrap justify-content-between align-items-center gap-2">
                    <div>Tổng tiền: <strong class="text-danger"><fmt:formatNumber value="${o.totalAmount}" pattern="#,##0"/> đ</strong></div>
                    <a class="btn btn-outline-primary btn-sm" href="${ctx}/orders/detail?id=${o.orderId}">Xem chi tiết</a>
                </div>
            </div>
        </c:forEach>
    </c:otherwise>
</c:choose>
