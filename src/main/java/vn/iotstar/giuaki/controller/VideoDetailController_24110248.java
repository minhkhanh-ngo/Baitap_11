package vn.iotstar.giuaki.controller;

import vn.iotstar.giuaki.services.IVideoService_24110248;
import vn.iotstar.giuaki.services.impl.VideoServiceImpl_24110248;
import vn.iotstar.giuaki.model.Video_24110248;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/video"})
public class VideoDetailController_24110248 extends HttpServlet {
    private IVideoService_24110248 videoService = new VideoServiceImpl_24110248();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String videoId = req.getParameter("id");
        Video_24110248 video = videoService.getVideoDetail(videoId);

        if (video != null) {
            req.setAttribute("v", video);
            req.getRequestDispatcher("/views/video-detail.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}