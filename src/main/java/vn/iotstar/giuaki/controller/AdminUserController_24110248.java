package vn.iotstar.giuaki.controller;

import vn.iotstar.giuaki.services.IUserService_24110248;
import vn.iotstar.giuaki.services.impl.UserServiceImpl_24110248;
import vn.iotstar.giuaki.model.User_24110248;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/admin/users", "/admin/users/edit", "/admin/users/delete", "/admin/users/save"})
public class AdminUserController_24110248 extends HttpServlet {
    private IUserService_24110248 userService = new UserServiceImpl_24110248();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getServletPath();

        if (path.equals("/admin/users/delete")) {
            String username = req.getParameter("id");
            userService.delete(username);
            resp.sendRedirect(req.getContextPath() + "/admin/users");
        }
        else if (path.equals("/admin/users/edit")) {
            String username = req.getParameter("id");
            if (username != null) {
                req.setAttribute("user", userService.findById(username));
            }
            req.getRequestDispatcher("/views/admin/user-form.jsp").forward(req, resp);
        }
        else {
            int page = 1;
            int limit = 6;
            if (req.getParameter("page") != null) {
                page = Integer.parseInt(req.getParameter("page"));
            }

            int offset = (page - 1) * limit;
            int totalUsers = userService.countUsers();
            int totalPages = (int) Math.ceil((double) totalUsers / limit);

            List<User_24110248> list = userService.findAll(offset, limit);
            req.setAttribute("users", list);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", totalPages);

            req.getRequestDispatcher("/views/admin/user-list.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        User_24110248 u = new User_24110248();
        u.setUsername(req.getParameter("username"));
        u.setPassword(req.getParameter("password"));
        u.setFullname(req.getParameter("fullname"));
        u.setEmail(req.getParameter("email"));
        u.setPhone(req.getParameter("phone"));
        u.setAdmin(req.getParameter("admin") != null);
        u.setActive(req.getParameter("active") != null);

        String isEdit = req.getParameter("isEdit");
        if ("true".equals(isEdit)) {
            userService.update(u);
        } else {
            userService.insert(u);
        }
        resp.sendRedirect(req.getContextPath() + "/admin/users");
    }
}