package vn.iotstar.giuaki.controller;

import vn.iotstar.giuaki.model.Order_24110248;
import vn.iotstar.giuaki.model.OrderStatus_24110248;
import vn.iotstar.giuaki.model.User_24110248;
import vn.iotstar.giuaki.services.IOrderService_24110248;
import vn.iotstar.giuaki.services.impl.OrderServiceImpl_24110248;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/orders", "/orders/detail"})
public class OrderHistoryController_24110248 extends HttpServlet {
    private final IOrderService_24110248 orderService = new OrderServiceImpl_24110248();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User_24110248 user = CartController_24110248.requireLogin(req, resp);
        if (user == null) return;

        if ("/orders/detail".equals(req.getServletPath())) {
            showDetail(req, resp, user);
        } else {
            showHistory(req, resp, user);
        }
    }

    private void showHistory(HttpServletRequest req, HttpServletResponse resp, User_24110248 user)
            throws ServletException, IOException {
        OrderStatus_24110248 filter = OrderStatus_24110248.fromCode(req.getParameter("status"));

        List<Order_24110248> orders = orderService.findByUser(user.getUsername(), filter);
        Map<String, Integer> byLabel = orderService.countByStatus(user.getUsername());

        Map<String, Integer> counts = new HashMap<>();
        int total = 0;
        for (Integer n : byLabel.values()) total += n;
        for (OrderStatus_24110248 s : OrderStatus_24110248.values()) {
            Integer n = byLabel.get(s.getLabel());
            counts.put(s.getCode(), n == null ? 0 : n);
        }

        req.setAttribute("orders", orders);
        req.setAttribute("statuses", OrderStatus_24110248.values());
        req.setAttribute("counts", counts);
        req.setAttribute("totalCount", total);
        req.setAttribute("currentStatus", filter);
        req.getRequestDispatcher("/views/order-history.jsp").forward(req, resp);
    }

    private void showDetail(HttpServletRequest req, HttpServletResponse resp, User_24110248 user)
            throws ServletException, IOException {
        int id;
        try {
            id = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }
        Order_24110248 order = orderService.findByIdAndUser(id, user.getUsername());
        if (order == null) {
            req.getSession().setAttribute(CartController_24110248.FLASH_ERROR, "Không tìm thấy đơn hàng.");
            resp.sendRedirect(req.getContextPath() + "/orders");
            return;
        }
        req.setAttribute("order", order);
        req.setAttribute("statuses", OrderStatus_24110248.values());
        req.getRequestDispatcher("/views/order-detail.jsp").forward(req, resp);
    }
}
