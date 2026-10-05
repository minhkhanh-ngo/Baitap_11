package vn.iotstar.giuaki.controller;

import vn.iotstar.giuaki.model.Cart_24110248;
import vn.iotstar.giuaki.model.Order_24110248;
import vn.iotstar.giuaki.model.User_24110248;
import vn.iotstar.giuaki.services.ICartService_24110248;
import vn.iotstar.giuaki.services.IOrderService_24110248;
import vn.iotstar.giuaki.services.IUserService_24110248;
import vn.iotstar.giuaki.services.OrderException_24110248;
import vn.iotstar.giuaki.services.impl.CartServiceImpl_24110248;
import vn.iotstar.giuaki.services.impl.OrderServiceImpl_24110248;
import vn.iotstar.giuaki.services.impl.UserServiceImpl_24110248;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@WebServlet(urlPatterns = {"/checkout", "/order/success"})
public class OrderController_24110248 extends HttpServlet {
    private static final String TOKEN_KEY = "checkoutToken";
    private static final Pattern PHONE = Pattern.compile("^(0|\\+84)\\d{9}$");

    private final ICartService_24110248 cartService = new CartServiceImpl_24110248();
    private final IOrderService_24110248 orderService = new OrderServiceImpl_24110248();
    private final IUserService_24110248 userService = new UserServiceImpl_24110248();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User_24110248 user = CartController_24110248.requireLogin(req, resp);
        if (user == null) return;

        if ("/order/success".equals(req.getServletPath())) {
            showSuccess(req, resp, user);
            return;
        }

        HttpSession session = req.getSession();
        Cart_24110248 cart = CartController_24110248.getCart(session);
        if (!prepareCheckout(req, resp, cart)) return;

        User_24110248 full = userService.findById(user.getUsername());
        req.setAttribute("receiverName", full != null && full.getFullname() != null ? full.getFullname() : user.getFullname());
        req.setAttribute("phone", full != null ? full.getPhone() : "");
        forwardForm(req, resp, session);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User_24110248 user = CartController_24110248.requireLogin(req, resp);
        if (user == null) return;
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        Cart_24110248 cart = CartController_24110248.getCart(session);

        String sentToken = req.getParameter("token");
        String sessionToken = (String) session.getAttribute(TOKEN_KEY);
        if (sentToken == null || !sentToken.equals(sessionToken)) {
            session.setAttribute(CartController_24110248.FLASH_ERROR,
                    cart.isEmpty() ? "Giỏ hàng đang trống!" : "Phiên thanh toán không hợp lệ, vui lòng thử lại.");
            resp.sendRedirect(req.getContextPath() + (cart.isEmpty() ? "/cart" : "/checkout"));
            return;
        }

        if (!prepareCheckout(req, resp, cart)) return;

        String receiverName = trim(req.getParameter("receiverName"));
        String phone = trim(req.getParameter("phone"));
        String address = trim(req.getParameter("address"));
        String note = trim(req.getParameter("note"));

        String error = null;
        if (receiverName.isEmpty() || receiverName.length() > 100) {
            error = "Vui lòng nhập họ tên người nhận (tối đa 100 ký tự).";
        } else if (!PHONE.matcher(phone).matches()) {
            error = "Số điện thoại không hợp lệ (VD: 0912345678).";
        } else if (address.isEmpty() || address.length() > 300) {
            error = "Vui lòng nhập địa chỉ giao hàng (tối đa 300 ký tự).";
        } else if (note.length() > 500) {
            error = "Ghi chú tối đa 500 ký tự.";
        }
        if (error != null) {
            req.setAttribute("error", error);
            req.setAttribute("receiverName", receiverName);
            req.setAttribute("phone", phone);
            req.setAttribute("address", address);
            req.setAttribute("note", note);
            forwardForm(req, resp, session);
            return;
        }

        try {
            Order_24110248 order = orderService.placeCodOrder(user.getUsername(), cart, receiverName, phone, address,
                    note.isEmpty() ? null : note);
            session.removeAttribute(TOKEN_KEY);
            cartService.clear(cart);
            resp.sendRedirect(req.getContextPath() + "/order/success?id=" + order.getOrderId());
        } catch (OrderException_24110248 e) {
            session.setAttribute(CartController_24110248.FLASH_ERROR, e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/cart");
        }
    }

    private boolean prepareCheckout(HttpServletRequest req, HttpServletResponse resp, Cart_24110248 cart) throws IOException {
        HttpSession session = req.getSession();
        List<String> warnings = cartService.refresh(cart);
        if (cart.isEmpty()) {
            session.setAttribute(CartController_24110248.FLASH_ERROR,
                    warnings.isEmpty() ? "Giỏ hàng đang trống, hãy chọn sản phẩm trước khi thanh toán." : join(warnings));
            resp.sendRedirect(req.getContextPath() + "/cart");
            return false;
        }
        if (!warnings.isEmpty()) {
            session.setAttribute(CartController_24110248.FLASH_ERROR, join(warnings) + " Vui lòng kiểm tra lại giỏ hàng.");
            resp.sendRedirect(req.getContextPath() + "/cart");
            return false;
        }
        return true;
    }

    private void forwardForm(HttpServletRequest req, HttpServletResponse resp, HttpSession session)
            throws ServletException, IOException {
        String token = UUID.randomUUID().toString();
        session.setAttribute(TOKEN_KEY, token);
        req.setAttribute("token", token);
        req.getRequestDispatcher("/views/checkout.jsp").forward(req, resp);
    }

    private void showSuccess(HttpServletRequest req, HttpServletResponse resp, User_24110248 user)
            throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/user/videos");
            return;
        }
        Order_24110248 order = orderService.findByIdAndUser(id, user.getUsername());
        if (order == null) {
            resp.sendRedirect(req.getContextPath() + "/user/videos");
            return;
        }
        req.setAttribute("order", order);
        req.getRequestDispatcher("/views/order-success.jsp").forward(req, resp);
    }

    private static String trim(String s) { return s == null ? "" : s.trim(); }

    private static String join(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            if (sb.length() > 0) sb.append(' ');
            sb.append(s);
        }
        return sb.toString();
    }
}
