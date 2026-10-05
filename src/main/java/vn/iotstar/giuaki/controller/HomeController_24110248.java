package vn.iotstar.giuaki.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/home", "/admin/home"})
public class HomeController_24110248 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if (path.equals("/admin/home")) {
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        } else {
            resp.sendRedirect(req.getContextPath() + "/user/videos");
        }
    }
}