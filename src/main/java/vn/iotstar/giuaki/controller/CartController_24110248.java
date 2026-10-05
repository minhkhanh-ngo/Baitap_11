package vn.iotstar.giuaki.controller;

import vn.iotstar.giuaki.model.Cart_24110248;
import vn.iotstar.giuaki.model.CartItem_24110248;
import vn.iotstar.giuaki.model.User_24110248;
import vn.iotstar.giuaki.services.ICartService_24110248;
import vn.iotstar.giuaki.services.impl.CartServiceImpl_24110248;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@WebServlet(urlPatterns = {"/cart", "/cart/add", "/cart/update", "/cart/remove", "/cart/clear"})
public class CartController_24110248 extends HttpServlet {
    public static final String CART_KEY = "cart";
    public static final String FLASH_SUCCESS = "flashSuccess";
    public static final String FLASH_ERROR = "flashError";

    private final ICartService_24110248 cartService = new CartServiceImpl_24110248();

    public static Cart_24110248 getCart(HttpSession session) {
        Cart_24110248 cart = (Cart_24110248) session.getAttribute(CART_KEY);
        if (cart == null) {
            cart = new Cart_24110248();
            session.setAttribute(CART_KEY, cart);
        }
        return cart;
    }

     public static User_24110248 requireLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        User_24110248 user = (User_24110248) session.getAttribute("loggedInUser");
        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        }
        return user;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (requireLogin(req, resp) == null) return;

        if (!"/cart".equals(req.getServletPath())) {
            resp.sendRedirect(req.getContextPath() + "/cart");
            return;
        }

        HttpSession session = req.getSession();
        Cart_24110248 cart = getCart(session);
        List<String> warnings = cartService.refresh(cart);
        if (!warnings.isEmpty()) {
            req.setAttribute("cartWarnings", warnings);
        }
        req.setAttribute("maxPerItem", Cart_24110248.MAX_QTY_PER_ITEM);
        req.getRequestDispatcher("/views/cart.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (requireLogin(req, resp) == null) return;
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        Cart_24110248 cart = getCart(session);
        String path = req.getServletPath();
        String videoId = req.getParameter("videoId");
        String cartUrl = req.getContextPath() + "/cart";
        String redirect = cartUrl;

        switch (path) {
            case "/cart/add": {
                String error = cartService.add(cart, videoId, req.getParameter("quantity"));
                if (error == null) {
                    session.setAttribute(FLASH_SUCCESS, "Đã thêm sản phẩm vào giỏ hàng.");
                } else {
                    session.setAttribute(FLASH_ERROR, error);
                }
                redirect = backOr(req, cartUrl);
                break;
            }
            case "/cart/update": {
                String error = cartService.update(cart, videoId, req.getParameter("quantity"));
                if (error == null) {
                    session.setAttribute(FLASH_SUCCESS, "Đã cập nhật số lượng.");
                } else {
                    session.setAttribute(FLASH_ERROR, error);
                }
                break;
            }
            case "/cart/remove": {
                CartItem_24110248 item = (videoId == null) ? null : cart.get(videoId);
                cartService.remove(cart, videoId);
                session.setAttribute(FLASH_SUCCESS, item == null
                        ? "Sản phẩm không có trong giỏ hàng."
                        : "Đã xóa \"" + item.getTitle() + "\" khỏi giỏ hàng.");
                break;
            }
            case "/cart/clear": {
                cartService.clear(cart);
                session.setAttribute(FLASH_SUCCESS, "Đã xóa toàn bộ giỏ hàng.");
                break;
            }
            default:
                break;
        }
        resp.sendRedirect(redirect);
    }


    private String backOr(HttpServletRequest req, String fallback) {
        String ref = req.getHeader("Referer");
        if (ref == null) return fallback;
        try {
            URI u = new URI(ref);
            String ctx = req.getContextPath();
            if (u.getHost() != null && u.getHost().equalsIgnoreCase(req.getServerName())
                    && u.getPath() != null && u.getPath().startsWith(ctx + "/")) {
                return ref;
            }
        } catch (Exception ignored) { }
        return fallback;
    }
}
