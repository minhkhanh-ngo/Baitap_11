<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>

<div style="max-width: 800px; margin: 20px auto; padding: 20px; background: #fff; border: 1px solid #ddd; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.05);">
    <h2 style="margin-top: 0; margin-bottom: 20px; color: #333;">Chi tiết Video</h2>
    <div style="display: flex; gap: 20px; align-items: flex-start;">
        <div style="flex: 0 0 220px;">
            <img src="${v.poster}" alt="Poster" style="width: 220px; height: 300px; object-fit: cover; border-radius: 6px; border: 1px solid #ccc;">
        </div>
        <div style="flex: 1;">
            <h3 style="margin-top: 0; color: #0056b3; margin-bottom: 15px;">${v.title}</h3>
            <p style="margin: 8px 0; color: #555;"><strong>Mã video:</strong> ${v.videoId}</p>
            <p style="margin: 8px 0; color: #555;"><strong>Thể loại:</strong> <span style="background: #6c757d; color: white; padding: 3px 8px; border-radius: 4px; font-size: 12px;">${v.categoryName}</span></p>
            <p style="margin: 8px 0 20px 0; color: #555;"><strong>Lượt xem:</strong> ${v.views}</p>
            <p style="margin: 8px 0; color: #dc3545; font-size: 22px; font-weight: bold;"><fmt:formatNumber value="${v.price}" pattern="#,##0"/> đ</p>
            <p style="margin: 8px 0 15px 0; color: #555;"><strong>Tình trạng:</strong>
                <c:choose>
                    <c:when test="${v.stock > 0}"><span style="color: #28a745; font-weight: bold;">Còn ${v.stock} sản phẩm</span></c:when>
                    <c:otherwise><span style="color: #dc3545; font-weight: bold;">Hết hàng</span></c:otherwise>
                </c:choose>
            </p>
            <c:if test="${v.stock > 0}">
                <form action="${pageContext.request.contextPath}/cart/add" method="post" style="display: flex; gap: 10px; align-items: center; margin-bottom: 20px;">
                    <input type="hidden" name="videoId" value="<c:out value='${v.videoId}'/>">
                    <label for="qty" style="font-weight: bold; color: #555;">Số lượng:</label>
                    <input type="number" id="qty" name="quantity" value="1" min="1" max="${v.stock > 10 ? 10 : v.stock}" style="width: 80px; padding: 6px; border: 1px solid #ccc; border-radius: 4px;" required>
                    <button type="submit" class="btn btn-primary">Thêm vào giỏ hàng</button>
                </form>
            </c:if>
            <div style="display: flex; gap: 15px;">
                <span style="background: #007bff; color: white; padding: 6px 12px; border-radius: 4px; font-weight: bold; font-size: 14px;">Share (${v.shareCount})</span>
                <span style="background: #dc3545; color: white; padding: 6px 12px; border-radius: 4px; font-weight: bold; font-size: 14px;">Like (${v.likeCount})</span>
            </div>
        </div>
    </div>

    <div style="margin-top: 25px; padding: 15px; border: 1px solid #e9ecef; background: #f8f9fa; border-radius: 6px;">
        <strong style="color: #333; display: block; margin-bottom: 8px;">Mô tả chi tiết:</strong>
        <p style="margin: 0; color: #555; line-height: 1.6;">${v.description}</p>
    </div>
</div>