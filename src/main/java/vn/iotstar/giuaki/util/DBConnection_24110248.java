package vn.iotstar.giuaki.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection_24110248 {
        public static Connection getConnection() throws Exception {
        String serverName = "localhost\\SQLEXPRESS";
        String dbName = "QuanLyVideo_De04";
        String userID = "sa";
        String password = "1234";

        String url = "jdbc:sqlserver://" + serverName + ";databaseName=" + dbName + ";encrypt=true;trustServerCertificate=true;";

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }

    public static void main(String[] args) {
        try {
            Connection conn = DBConnection_24110248.getConnection();
            if (conn != null) {
                System.out.println("Kết nối CSDL QuanLyVideo_De04 thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}