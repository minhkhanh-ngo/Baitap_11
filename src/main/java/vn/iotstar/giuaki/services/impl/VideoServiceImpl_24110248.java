package vn.iotstar.giuaki.services.impl;

import vn.iotstar.giuaki.model.Video_24110248;
import vn.iotstar.giuaki.controller.CategoryCount_24110248;
import vn.iotstar.giuaki.services.IVideoService_24110248;
import vn.iotstar.giuaki.util.DBConnection_24110248;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoServiceImpl_24110248 implements IVideoService_24110248 {

    @Override
    public Video_24110248 getVideoDetail(String videoId) {
        String sql = "SELECT v.VideoId, v.Title, v.Poster, v.Views, v.Description, v.Active, v.Price, v.Stock, c.Categoryname, " +
                "(SELECT COUNT(*) FROM Shares s WHERE s.VideoId = v.VideoId) AS ShareCount, " +
                "(SELECT COUNT(*) FROM Favorites f WHERE f.VideoId = v.VideoId) AS LikeCount " +
                "FROM Videos v JOIN Category c ON v.CategoryId = c.CategoryId WHERE v.VideoId = ?";

        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, videoId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Video_24110248 v = new Video_24110248();
                v.setVideoId(rs.getString("VideoId"));
                v.setTitle(rs.getString("Title"));
                v.setPoster(rs.getString("Poster"));
                v.setViews(rs.getInt("Views"));
                v.setPrice(rs.getLong("Price"));
                v.setStock(rs.getInt("Stock"));
                v.setDescription(rs.getString("Description"));
                v.setActive(rs.getBoolean("Active"));
                v.setCategoryName(rs.getString("Categoryname"));
                v.setShareCount(rs.getInt("ShareCount"));
                v.setLikeCount(rs.getInt("LikeCount"));
                return v;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Video_24110248> getVideosByCategory(int categoryId) {
        List<Video_24110248> list = new ArrayList<>();
        String sql = "SELECT v.VideoId, v.Title, v.Poster, v.Views, v.Price, v.Stock, c.Categoryname " +
                "FROM Videos v JOIN Category c ON v.CategoryId = c.CategoryId " +
                "WHERE v.CategoryId = ?";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Video_24110248 v = new Video_24110248();
                v.setVideoId(rs.getString("VideoId"));
                v.setTitle(rs.getString("Title"));
                v.setPoster(rs.getString("Poster"));
                v.setViews(rs.getInt("Views"));
                v.setPrice(rs.getLong("Price"));
                v.setStock(rs.getInt("Stock"));
                v.setCategoryName(rs.getString("Categoryname"));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<CategoryCount_24110248> getCategoryVideoCounts() {
        List<CategoryCount_24110248> list = new ArrayList<>();
        String sql = "SELECT c.CategoryId, c.Categoryname, COUNT(v.VideoId) AS VideoCount " +
                "FROM Category c LEFT JOIN Videos v ON c.CategoryId = v.CategoryId " +
                "GROUP BY c.CategoryId, c.Categoryname";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CategoryCount_24110248 cc = new CategoryCount_24110248();
                cc.setCategoryId(rs.getInt("CategoryId"));
                cc.setCategoryName(rs.getString("Categoryname"));
                cc.setVideoCount(rs.getInt("VideoCount"));
                list.add(cc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Video_24110248> getVideosByCategoryPaginate(int categoryId, int page, int pageSize) {
        List<Video_24110248> list = new ArrayList<>();
        String sql = "SELECT v.VideoId, v.Title, v.Poster, v.Views, v.Price, v.Stock, c.Categoryname " +
                "FROM Videos v JOIN Category c ON v.CategoryId = c.CategoryId " +
                "WHERE v.CategoryId = ? " +
                "ORDER BY v.VideoId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int offset = (page - 1) * pageSize;
            ps.setInt(1, categoryId);
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Video_24110248 v = new Video_24110248();
                v.setVideoId(rs.getString("VideoId"));
                v.setTitle(rs.getString("Title"));
                v.setPoster(rs.getString("Poster"));
                v.setViews(rs.getInt("Views"));
                v.setPrice(rs.getLong("Price"));
                v.setStock(rs.getInt("Stock"));
                v.setCategoryName(rs.getString("Categoryname"));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countVideosByCategory(int categoryId) {
        String sql = "SELECT COUNT(*) FROM Videos WHERE CategoryId = ?";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Video_24110248> getAllVideosPaginate(int page, int pageSize) {
        List<Video_24110248> list = new ArrayList<>();
        String sql = "SELECT v.VideoId, v.Title, v.Poster, v.Views, v.Price, v.Stock, c.Categoryname " +
                "FROM Videos v JOIN Category c ON v.CategoryId = c.CategoryId " +
                "ORDER BY v.VideoId OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int offset = (page - 1) * pageSize;
            ps.setInt(1, offset);
            ps.setInt(2, pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Video_24110248 v = new Video_24110248();
                v.setVideoId(rs.getString("VideoId"));
                v.setTitle(rs.getString("Title"));
                v.setPoster(rs.getString("Poster"));
                v.setViews(rs.getInt("Views"));
                v.setPrice(rs.getLong("Price"));
                v.setStock(rs.getInt("Stock"));
                v.setCategoryName(rs.getString("Categoryname"));
                list.add(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public int countAllVideos() {
        String sql = "SELECT COUNT(*) FROM Videos";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}