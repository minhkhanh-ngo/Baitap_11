package vn.iotstar.giuaki.controller;

import vn.iotstar.giuaki.dao.UserDAO_24110248;
import vn.iotstar.giuaki.model.User_24110248;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginController_24110248 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String user = req.getParameter("username");
        String pass = req.getParameter("password");

        UserDAO_24110248 dao = new UserDAO_24110248();
        User_24110248 account = dao.login(user, pass);

        if (account != null) {
            HttpSession session = req.getSession();
            session.setAttribute("loggedInUser", account);
            session.removeAttribute("cart"); // mỗi lần đăng nhập bắt đầu với giỏ hàng trống

            if (account.isAdmin()) {
                resp.sendRedirect(req.getContextPath() + "/admin/home");
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        } else {
            req.setAttribute("error", "Sai tài khoản, mật khẩu hoặc chưa kích hoạt!");
            req.getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }
}