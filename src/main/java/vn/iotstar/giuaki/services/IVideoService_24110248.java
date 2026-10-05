package vn.iotstar.giuaki.services;

import vn.iotstar.giuaki.controller.CategoryCount_24110248;
import vn.iotstar.giuaki.model.Video_24110248;
import java.util.List;

public interface IVideoService_24110248 {
    Video_24110248 getVideoDetail(String videoId);
    List<Video_24110248> getVideosByCategory(int categoryId);
    List<CategoryCount_24110248> getCategoryVideoCounts();
    List<Video_24110248> getVideosByCategoryPaginate(int categoryId, int page, int pageSize);
    int countVideosByCategory(int categoryId);
    List<Video_24110248> getAllVideosPaginate(int page, int pageSize);
    int countAllVideos();
}