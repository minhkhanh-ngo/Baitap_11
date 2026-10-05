package vn.iotstar.giuaki.controller;
import vn.iotstar.giuaki.model.User_24110248;
import vn.iotstar.giuaki.util.EmailUtil_24110248;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Random;

@WebServlet(urlPatterns = {"/register"})
public class RegisterController_24110248 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User_24110248 user = new User_24110248();
        user.setUsername(req.getParameter("username"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));
        user.setFullname(req.getParameter("fullname"));
        user.setPhone(req.getParameter("phone"));

        // Tạo OTP
        String otp = String.format("%06d", new Random().nextInt(999999));

        HttpSession session = req.getSession();
        session.setAttribute("tempUser", user);
        session.setAttribute("otp", otp);

        EmailUtil_24110248.sendOTP(user.getEmail(), otp);
        resp.sendRedirect(req.getContextPath() + "/verify");
    }
}