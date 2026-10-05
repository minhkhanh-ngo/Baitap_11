<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="vi_VN"/>

<div style="display: flex; gap: 30px; width: 100%;">

    <div style="width: 260px; flex-shrink: 0; background: #fff; border: 1px solid #ddd; border-radius: 8px; padding: 15px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); height: fit-content;">
        <h3 style="margin-top: 0; font-size: 18px; color: #333; border-bottom: 2px solid #0056b3; padding-bottom: 10px; margin-bottom: 15px;">Danh mục Thể loại</h3>
        <ul style="list-style-type: none; padding: 0; margin: 0;">
            <li style="margin-bottom: 8px;">
                <a href="${pageContext.request.contextPath}/user/videos" style="display: flex; justify-content: space-between; align-items: center; padding: 8px 12px; text-decoration: none; color: ${empty currentCategory ? '#fff' : '#333'}; background: ${empty currentCategory ? '#0056b3' : '#f8f9fa'}; border-radius: 4px; font-size: 14px; font-weight: 500;">
                    <span>Tất cả video</span>
                </a>
            </li>
            <c:forEach var="cat" items="${categories}">
                <li style="margin-bottom: 8px;">
                    <a href="${pageContext.request.contextPath}/user/videos?id=${cat.categoryId}" style="display: flex; justify-content: space-between; align-items: center; padding: 8px 12px; text-decoration: none; color: ${currentCategory == cat.categoryId ? '#fff' : '#333'}; background: ${currentCategory == cat.categoryId ? '#0056b3' : '#f8f9fa'}; border-radius: 4px; font-size: 14px; font-weight: 500;">
                        <span>${cat.categoryName}</span>
                        <span style="background: ${currentCategory == cat.categoryId ? '#fff' : '#6c757d'}; color: ${currentCategory == cat.categoryId ? '#0056b3' : 'white'}; padding: 2px 6px; border-radius: 10px; font-size: 11px;">${cat.videoCount}</span>
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>

    <div style="flex: 1; display: flex; flex-direction: column;">
        <h2 style="margin-top: 0; margin-bottom: 20px; color: #333;">Danh sách Video</h2>
        <div style="display: flex; flex-wrap: wrap; gap: 20px; min-height: 250px;">
            <c:forEach var="v" items="${videos}">
                <div style="width: 220px; background: #fff; border: 1px solid #ddd; border-radius: 8px; overflow: hidden; box-shadow: 0 2px 5px rgba(0,0,0,0.05); display: flex; flex-direction: column;">
                    <img src="${v.poster}" alt="Poster" style="width: 100%; height: 160px; object-fit: cover;">
                    <div style="padding: 12px; display: flex; flex-direction: column; flex: 1; text-align: center;">
                        <h4 style="margin: 0 0 10px 0; font-size: 15px; font-weight: bold;">
                            <a href="${pageContext.request.contextPath}/video?id=${v.videoId}" style="text-decoration: none; color: #0056b3;">${v.title}</a>
                        </h4>
                        <p style="margin: auto 0 4px 0; color: #666; font-size: 13px;">Lượt xem: ${v.views}</p>
                        <p style="margin: 0 0 8px 0; color: #dc3545; font-weight: bold;"><fmt:formatNumber value="${v.price}" pattern="#,##0"/> đ</p>
                        <c:choose>
                            <c:when test="${v.stock > 0}">
                                <form action="${pageContext.request.contextPath}/cart/add" method="post" style="margin: 0;">
                                    <input type="hidden" name="videoId" value="<c:out value='${v.videoId}'/>">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="btn btn-sm btn-primary w-100">Thêm vào giỏ</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-sm btn-secondary w-100" disabled>Hết hàng</button>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
        </div>

        <c:if test="${totalPages > 1}">
            <nav aria-label="Page navigation" style="margin-top: 30px;">
                <ul class="pagination justify-content-center">
                    <c:forEach begin="1" end="${totalPages}" var="i">
                        <li class="page-item ${currentPage == i ? 'active' : ''}">
                            <a class="page-link" href="${pageContext.request.contextPath}/user/videos${not empty currentCategory ? '?id='.concat(currentCategory).concat('&page=').concat(i) : '?page='.concat(i)}">${i}</a>
                        </li>
                    </c:forEach>
                </ul>
            </nav>
        </c:if>
    </div>
</div>