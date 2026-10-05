package vn.iotstar.giuaki.services.impl;

import vn.iotstar.giuaki.model.User_24110248;
import vn.iotstar.giuaki.services.IUserService_24110248;
import vn.iotstar.giuaki.util.DBConnection_24110248;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl_24110248 implements IUserService_24110248 {

    public int countUsers() {
        String sql = "SELECT COUNT(*) FROM Users";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    public List<User_24110248> findAll(int offset, int limit) {
        List<User_24110248> list = new ArrayList<>();
        String sql = "SELECT * FROM Users ORDER BY Username OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, offset);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User_24110248 u = new User_24110248();
                u.setUsername(rs.getString("Username"));
                u.setFullname(rs.getString("Fullname"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setAdmin(rs.getBoolean("Admin"));
                u.setActive(rs.getBoolean("Active"));
                list.add(u);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public User_24110248 findById(String username) {
        String sql = "SELECT * FROM Users WHERE Username=?";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User_24110248 u = new User_24110248();
                u.setUsername(rs.getString("Username"));
                u.setPassword(rs.getString("Password"));
                u.setFullname(rs.getString("Fullname"));
                u.setEmail(rs.getString("Email"));
                u.setPhone(rs.getString("Phone"));
                u.setAdmin(rs.getBoolean("Admin"));
                u.setActive(rs.getBoolean("Active"));
                return u;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean insert(User_24110248 u) {
        String sql = "INSERT INTO Users(Username, Password, Phone, Fullname, Email, Admin, Active) VALUES(?,?,?,?,?,?,?)";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getPhone());
            ps.setString(4, u.getFullname());
            ps.setString(5, u.getEmail());
            ps.setBoolean(6, u.isAdmin());
            ps.setBoolean(7, u.isActive());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean update(User_24110248 u) {
        String sql = "UPDATE Users SET Password=?, Fullname=?, Email=?, Phone=?, Admin=?, Active=? WHERE Username=?";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getPassword());
            ps.setString(2, u.getFullname());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPhone());
            ps.setBoolean(5, u.isAdmin());
            ps.setBoolean(6, u.isActive());
            ps.setString(7, u.getUsername());
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }

    public boolean delete(String username) {
        String sql = "DELETE FROM Users WHERE Username=?";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}