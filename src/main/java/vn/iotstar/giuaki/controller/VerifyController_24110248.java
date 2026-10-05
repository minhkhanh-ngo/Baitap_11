package vn.iotstar.giuaki.controller;
import vn.iotstar.giuaki.dao.UserDAO_24110248;
import vn.iotstar.giuaki.model.User_24110248;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/verify"})
public class VerifyController_24110248 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/views/verify.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String inputOtp = req.getParameter("otp");
        HttpSession session = req.getSession();
        String sessionOtp = (String) session.getAttribute("otp");
        User_24110248 tempUser = (User_24110248) session.getAttribute("tempUser");

        if (sessionOtp != null && sessionOtp.equals(inputOtp)) {
            UserDAO_24110248 dao = new UserDAO_24110248();
            dao.insertUser(tempUser); // Lưu vào DB
            session.removeAttribute("otp");
            session.removeAttribute("tempUser");
            resp.sendRedirect(req.getContextPath() + "/login?msg=success");
        } else {
            req.setAttribute("error", "Mã OTP không hợp lệ!");
            req.getRequestDispatcher("/views/verify.jsp").forward(req, resp);
        }
    }
}