package vn.iotstar.giuaki.services.impl;

import vn.iotstar.giuaki.model.Cart_24110248;
import vn.iotstar.giuaki.model.CartItem_24110248;
import vn.iotstar.giuaki.model.Order_24110248;
import vn.iotstar.giuaki.model.OrderDetail_24110248;
import vn.iotstar.giuaki.services.IOrderService_24110248;
import vn.iotstar.giuaki.services.OrderException_24110248;
import vn.iotstar.giuaki.util.DBConnection_24110248;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderServiceImpl_24110248 implements IOrderService_24110248 {

    @Override
    public Order_24110248 placeCodOrder(String username, Cart_24110248 cart, String receiverName,
                                        String phone, String address, String note) throws OrderException_24110248 {
        if (cart == null || cart.isEmpty()) {
            throw new OrderException_24110248("Giỏ hàng đang trống!");
        }

        // Khóa theo thứ tự VideoId cố định để hai đơn đồng thời không bị deadlock.
        List<CartItem_24110248> items = new ArrayList<>(cart.getItems());
        Collections.sort(items, new Comparator<CartItem_24110248>() {
            @Override
            public int compare(CartItem_24110248 a, CartItem_24110248 b) {
                return a.getVideoId().compareTo(b.getVideoId());
            }
        });

        Connection conn = null;
        try {
            conn = DBConnection_24110248.getConnection();
            conn.setAutoCommit(false);

            // 1. Khóa dòng, kiểm tra tồn kho + lấy giá hiện hành từ CSDL
            List<OrderDetail_24110248> details = new ArrayList<>();
            long total = 0;
            String lockSql = "SELECT Title, Price, Stock, Active FROM Videos WITH (UPDLOCK, ROWLOCK) WHERE VideoId = ?";
            try (PreparedStatement ps = conn.prepareStatement(lockSql)) {
                for (CartItem_24110248 it : items) {
                    if (it.getQuantity() < 1 || it.getQuantity() > Cart_24110248.MAX_QTY_PER_ITEM) {
                        throw new OrderException_24110248("Số lượng sản phẩm \"" + it.getTitle() + "\" không hợp lệ.");
                    }
                    ps.setString(1, it.getVideoId());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next() || !rs.getBoolean("Active")) {
                            throw new OrderException_24110248("Sản phẩm \"" + it.getTitle() + "\" không còn được bán.");
                        }
                        String title = rs.getString("Title");
                        long price = rs.getLong("Price");
                        int stock = rs.getInt("Stock");
                        if (it.getQuantity() > stock) {
                            throw new OrderException_24110248("Sản phẩm \"" + title + "\" chỉ còn " + stock + " cái trong kho.");
                        }
                        OrderDetail_24110248 d = new OrderDetail_24110248();
                        d.setVideoId(it.getVideoId());
                        d.setTitle(title);
                        d.setQuantity(it.getQuantity());
                        d.setUnitPrice(price);
                        details.add(d);
                        total += d.getSubtotal();
                    }
                }
            }

            // 2. Tạo đơn hàng
            Order_24110248 order = new Order_24110248();
            order.setUsername(username);
            order.setReceiverName(receiverName);
            order.setPhone(phone);
            order.setAddress(address);
            order.setNote(note);
            order.setTotalAmount(total);
            order.setPaymentMethod(Order_24110248.PAYMENT_COD);
            order.setStatus(Order_24110248.STATUS_PENDING);

            String insertOrder = "INSERT INTO Orders(Username, ReceiverName, Phone, Address, Note, TotalAmount, PaymentMethod, Status) "
                    + "VALUES(?,?,?,?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, username);
                ps.setString(2, receiverName);
                ps.setString(3, phone);
                ps.setString(4, address);
                ps.setString(5, note);
                ps.setLong(6, total);
                ps.setString(7, order.getPaymentMethod());
                ps.setString(8, order.getStatus());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (!keys.next()) throw new SQLException("Không lấy được mã đơn hàng.");
                    order.setOrderId(keys.getInt(1));
                }
            }

            // 3. Chi tiết đơn + trừ tồn kho
            String insertDetail = "INSERT INTO OrderDetails(OrderId, VideoId, Quantity, UnitPrice) VALUES(?,?,?,?)";
            String updateStock = "UPDATE Videos SET Stock = Stock - ? WHERE VideoId = ? AND Stock >= ?";
            try (PreparedStatement psD = conn.prepareStatement(insertDetail);
                 PreparedStatement psS = conn.prepareStatement(updateStock)) {
                for (OrderDetail_24110248 d : details) {
                    psD.setInt(1, order.getOrderId());
                    psD.setString(2, d.getVideoId());
                    psD.setInt(3, d.getQuantity());
                    psD.setLong(4, d.getUnitPrice());
                    psD.executeUpdate();

                    psS.setInt(1, d.getQuantity());
                    psS.setString(2, d.getVideoId());
                    psS.setInt(3, d.getQuantity());
                    if (psS.executeUpdate() == 0) {
                        throw new OrderException_24110248("Sản phẩm \"" + d.getTitle() + "\" vừa hết hàng, vui lòng kiểm tra lại giỏ.");
                    }
                }
            }

            conn.commit();
            order.setDetails(details);
            return order;
        } catch (OrderException_24110248 e) {
            rollbackQuietly(conn);
            throw e;
        } catch (Exception e) {
            rollbackQuietly(conn);
            e.printStackTrace();
            throw new OrderException_24110248("Không thể đặt hàng lúc này, vui lòng thử lại sau.");
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); } catch (SQLException ignored) {}
                try { conn.close(); } catch (SQLException ignored) {}
            }
        }
    }

    private void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try { conn.rollback(); } catch (SQLException ignored) {}
        }
    }

    @Override
    public Order_24110248 findByIdAndUser(int orderId, String username) {
        String sqlOrder = "SELECT * FROM Orders WHERE OrderId = ? AND Username = ?";
        String sqlDetail = "SELECT d.VideoId, v.Title, d.Quantity, d.UnitPrice "
                + "FROM OrderDetails d JOIN Videos v ON d.VideoId = v.VideoId "
                + "WHERE d.OrderId = ? ORDER BY d.OrderDetailId";
        try (Connection conn = DBConnection_24110248.getConnection()) {
            Order_24110248 o = null;
            try (PreparedStatement ps = conn.prepareStatement(sqlOrder)) {
                ps.setInt(1, orderId);
                ps.setString(2, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        o = new Order_24110248();
                        o.setOrderId(rs.getInt("OrderId"));
                        o.setUsername(rs.getString("Username"));
                        o.setReceiverName(rs.getString("ReceiverName"));
                        o.setPhone(rs.getString("Phone"));
                        o.setAddress(rs.getString("Address"));
                        o.setNote(rs.getString("Note"));
                        o.setTotalAmount(rs.getLong("TotalAmount"));
                        o.setPaymentMethod(rs.getString("PaymentMethod"));
                        o.setStatus(rs.getString("Status"));
                        o.setCreatedDate(rs.getTimestamp("CreatedDate"));
                    }
                }
            }
            if (o == null) return null;
            try (PreparedStatement ps = conn.prepareStatement(sqlDetail)) {
                ps.setInt(1, orderId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        OrderDetail_24110248 d = new OrderDetail_24110248();
                        d.setVideoId(rs.getString("VideoId"));
                        d.setTitle(rs.getString("Title"));
                        d.setQuantity(rs.getInt("Quantity"));
                        d.setUnitPrice(rs.getLong("UnitPrice"));
                        o.getDetails().add(d);
                    }
                }
            }
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
