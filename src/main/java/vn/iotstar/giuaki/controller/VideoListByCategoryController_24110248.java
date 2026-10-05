package vn.iotstar.giuaki.controller;

import vn.iotstar.giuaki.services.IVideoService_24110248;
import vn.iotstar.giuaki.services.impl.VideoServiceImpl_24110248;
import vn.iotstar.giuaki.model.Video_24110248;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/user/videos"})
public class VideoListByCategoryController_24110248 extends HttpServlet {
    private IVideoService_24110248 videoService = new VideoServiceImpl_24110248();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer categoryId = null;
        String idStr = req.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                categoryId = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                categoryId = null;
            }
        }

        int pageSize = 3;
        int currentPage = 1;
        String pageStr = req.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            try {
                currentPage = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                currentPage = 1;
            }
        }

        List<Video_24110248> list;
        int totalVideos;

        if (categoryId != null) {
            list = videoService.getVideosByCategoryPaginate(categoryId, currentPage, pageSize);
            totalVideos = videoService.countVideosByCategory(categoryId);
        } else {
            list = videoService.getAllVideosPaginate(currentPage, pageSize);
            totalVideos = videoService.countAllVideos();
        }

        int totalPages = (int) Math.ceil((double) totalVideos / pageSize);
        List<CategoryCount_24110248> categories = videoService.getCategoryVideoCounts();

        req.setAttribute("videos", list);
        req.setAttribute("categories", categories);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("currentCategory", categoryId);

        req.getRequestDispatcher("/views/video-list.jsp").forward(req, resp);
    }
}