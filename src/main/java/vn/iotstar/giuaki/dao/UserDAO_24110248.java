package vn.iotstar.giuaki.dao;
import vn.iotstar.giuaki.model.User_24110248;
import vn.iotstar.giuaki.util.DBConnection_24110248;
import java.sql.*;

public class UserDAO_24110248 {
    public User_24110248 login(String username, String password) {
        String sql = "SELECT * FROM Users WHERE Username=? AND Password=? AND Active=1";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User_24110248 u = new User_24110248();
                u.setUsername(rs.getString("Username"));
                u.setFullname(rs.getString("Fullname"));
                u.setEmail(rs.getString("Email"));
                u.setAdmin(rs.getBoolean("Admin"));
                u.setActive(rs.getBoolean("Active"));
                return u;
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean insertUser(User_24110248 u) {
        String sql = "INSERT INTO Users(Username, Password, Phone, Fullname, Email, Admin, Active) VALUES(?,?,?,?,?,?,1)";
        try (Connection conn = DBConnection_24110248.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
            ps.setString(3, u.getPhone());
            ps.setString(4, u.getFullname());
            ps.setString(5, u.getEmail());
            ps.setBoolean(6, false);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { e.printStackTrace(); }
        return false;
    }
}