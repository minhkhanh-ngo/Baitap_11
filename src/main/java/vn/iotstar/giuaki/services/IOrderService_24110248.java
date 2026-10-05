package vn.iotstar.giuaki.services;

import vn.iotstar.giuaki.model.Cart_24110248;
import vn.iotstar.giuaki.model.Order_24110248;

public interface IOrderService_24110248 {
    /**
     * Tạo đơn hàng thanh toán khi nhận hàng (COD) từ giỏ hàng, trừ tồn kho.
     * Toàn bộ chạy trong một transaction; giá được lấy lại từ CSDL.
     */
    Order_24110248 placeCodOrder(String username, Cart_24110248 cart, String receiverName,
                                 String phone, String address, String note) throws OrderException_24110248;

    /** Lấy đơn hàng (kèm chi tiết) của đúng người dùng; null nếu không tồn tại / không thuộc về họ. */
    Order_24110248 findByIdAndUser(int orderId, String username);
}
