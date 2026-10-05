package vn.iotstar.giuaki.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/logout"})
public class LogoutController_24110248 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.removeAttribute("loggedInUser");
        session.invalidate();

        resp.sendRedirect(req.getContextPath() + "/login");
    }
}