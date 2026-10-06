<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>

<h2 class="mb-4">Giỏ hàng của bạn</h2>

<c:if test="${not empty cartWarnings}">
    <div class="alert alert-warning">
        <c:forEach var="w" items="${cartWarnings}"><div><c:out value="${w}"/></div></c:forEach>
    </div>
</c:if>

<c:choose>
    <c:when test="${empty sessionScope.cart or sessionScope.cart.size == 0}">
        <div class="text-center py-5">
            <p class="text-muted fs-5">Giỏ hàng của bạn đang trống.</p>
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/user/videos">Tiếp tục mua sắm</a>
        </div>
    </c:when>
    <c:otherwise>
        <div class="table-responsive">
            <table class="table align-middle">
                <thead class="table-light">
                <tr>
                    <th style="width:90px"></th>
                    <th>Sản phẩm</th>
                    <th class="text-end">Đơn giá</th>
                    <th style="width:220px" class="text-center">Số lượng</th>
                    <th class="text-end">Thành tiền</th>
                    <th style="width:90px"></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="item" items="${sessionScope.cart.items}">
                    <tr>
                        <td><img src="<c:out value='${item.poster}'/>" alt="" style="width:80px;height:56px;object-fit:cover;" class="rounded"></td>
                        <td>
                            <a class="fw-semibold text-decoration-none" href="${pageContext.request.contextPath}/video?id=${item.videoId}"><c:out value="${item.title}"/></a>
                            <div class="small text-muted">Còn ${item.stock} sản phẩm · Tối đa ${item.maxQuantity}/đơn</div>
                        </td>
                        <td class="text-end"><fmt:formatNumber value="${item.price}" pattern="#,##0"/> đ</td>
                        <td>
                            <form action="${pageContext.request.contextPath}/cart/update" method="post" class="d-flex gap-2 justify-content-center">
                                <input type="hidden" name="videoId" value="<c:out value='${item.videoId}'/>">
                                <input type="number" name="quantity" value="${item.quantity}" min="1" max="${item.maxQuantity}"
                                       class="form-control form-control-sm" style="width:80px" required
                                       onchange="this.form.requestSubmit()"
                                       onkeydown="if(event.key==='Enter'){event.preventDefault();this.form.requestSubmit();}">
                            </form>
                        </td>
                        <td class="text-end fw-semibold"><fmt:formatNumber value="${item.subtotal}" pattern="#,##0"/> đ</td>
                        <td class="text-end">
                            <form action="${pageContext.request.contextPath}/cart/remove" method="post"
                                  onsubmit="return confirm('Xóa sản phẩm này khỏi giỏ hàng?');">
                                <input type="hidden" name="videoId" value="<c:out value='${item.videoId}'/>">
                                <button type="submit" class="btn btn-sm btn-outline-danger">Xóa</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
                <tfoot>
                <tr>
                    <td colspan="4" class="text-end fs-5">Tổng cộng (${sessionScope.cart.totalQuantity} sản phẩm):</td>
                    <td class="text-end fs-5 fw-bold text-danger"><fmt:formatNumber value="${sessionScope.cart.totalAmount}" pattern="#,##0"/> đ</td>
                    <td></td>
                </tr>
                </tfoot>
            </table>
        </div>

        <div class="d-flex justify-content-between align-items-center flex-wrap gap-2 mt-3">
            <div class="d-flex align-items-center gap-2">
                <a class="btn btn-sm btn-outline-secondary text-nowrap" style="width:170px" href="${pageContext.request.contextPath}/user/videos">← Tiếp tục mua sắm</a>
                <form action="${pageContext.request.contextPath}/cart/clear" method="post"
                      onsubmit="return confirm('Xóa toàn bộ giỏ hàng?');">
                    <button type="submit" class="btn btn-sm btn-outline-danger text-nowrap" style="width:170px">Xóa toàn bộ giỏ</button>
                </form>
            </div>
            <a class="btn btn-success btn-lg" href="${pageContext.request.contextPath}/checkout">Thanh toán (COD) →</a>
        </div>
    </c:otherwise>
</c:choose>
