package vn.iotstar.giuaki.services;

import vn.iotstar.giuaki.model.Cart_24110248;
import java.util.List;

/**
 * Nghiệp vụ giỏ hàng. Các hàm thay đổi giỏ trả về thông báo lỗi (tiếng Việt)
 * hoặc null nếu thành công.
 */
public interface ICartService_24110248 {
    /** Thêm sản phẩm; nếu đã có thì cộng dồn số lượng (không vượt giới hạn). */
    String add(Cart_24110248 cart, String videoId, String quantityStr);

    /** Đặt lại số lượng của một dòng trong giỏ. */
    String update(Cart_24110248 cart, String videoId, String quantityStr);

    /** Xóa một dòng khỏi giỏ. */
    void remove(Cart_24110248 cart, String videoId);

    void clear(Cart_24110248 cart);

    /**
     * Đồng bộ giỏ với CSDL (giá, tồn kho, trạng thái bán). Dòng hết hàng/ngừng bán bị gỡ,
     * số lượng vượt tồn kho bị giảm. Trả về danh sách cảnh báo (rỗng nếu không có thay đổi).
     */
    List<String> refresh(Cart_24110248 cart);
}
