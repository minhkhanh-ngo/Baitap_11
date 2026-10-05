<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row justify-content-center">
    <div class="col-md-12">
        <div class="card shadow-sm border-0 rounded-4 p-4">
            <h3 class="text-center mb-4 text-primary fw-bold">Đăng Ký Tài Khoản</h3>
            <form action="${pageContext.request.contextPath}/register" method="post">
                <div class="mb-3">
                    <label class="form-label fw-semibold">Username:</label>
                    <input type="text" name="username" class="form-control rounded-3" required>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Password:</label>
                    <input type="password" name="password" class="form-control rounded-3" required>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Họ tên:</label>
                    <input type="text" name="fullname" class="form-control rounded-3" required>
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Số điện thoại:</label>
                    <input type="text" name="phone" class="form-control rounded-3">
                </div>
                <div class="mb-3">
                    <label class="form-label fw-semibold">Email (Để nhận OTP):</label>
                    <input type="email" name="email" class="form-control rounded-3" required>
                </div>
                <div class="d-grid mb-3">
                    <button type="submit" class="btn btn-primary rounded-3 py-2 fw-bold">Đăng ký & Nhận OTP</button>
                </div>
            </form>
            <div class="text-center">
                <a href="${pageContext.request.contextPath}/login" class="text-decoration-none text-primary">Đã có tài khoản? Đăng nhập</a>
            </div>
        </div>
    </div>
</div>