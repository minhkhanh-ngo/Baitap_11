package vn.iotstar.giuaki.services;

/** Lỗi nghiệp vụ khi đặt hàng (hết hàng, giỏ trống...). Message hiển thị được cho người dùng. */
public class OrderException_24110248 extends Exception {
    private static final long serialVersionUID = 1L;

    public OrderException_24110248(String message) {
        super(message);
    }
}
