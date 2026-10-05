package vn.iotstar.giuaki.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/** Giỏ hàng của một người dùng, lưu trong HttpSession với key "cart". */
public class Cart_24110248 implements Serializable {
    private static final long serialVersionUID = 1L;

    /** Giới hạn số lượng tối đa cho mỗi sản phẩm trong một đơn. */
    public static final int MAX_QTY_PER_ITEM = 10;

    private final Map<String, CartItem_24110248> items = new LinkedHashMap<>();

    public static int maxAllowed(int stock) {
        return Math.max(0, Math.min(stock, MAX_QTY_PER_ITEM));
    }

    public Collection<CartItem_24110248> getItems() { return new ArrayList<>(items.values()); }
    public CartItem_24110248 get(String videoId) { return items.get(videoId); }
    public void put(CartItem_24110248 item) { items.put(item.getVideoId(), item); }
    public void remove(String videoId) { items.remove(videoId); }
    public void clear() { items.clear(); }
    public boolean isEmpty() { return items.isEmpty(); }

    /** Số loại sản phẩm khác nhau. */
    public int getSize() { return items.size(); }

    /** Tổng số lượng (cộng dồn) – hiển thị badge trên menu. */
    public int getTotalQuantity() {
        int n = 0;
        for (CartItem_24110248 i : items.values()) n += i.getQuantity();
        return n;
    }

    public long getTotalAmount() {
        long sum = 0;
        for (CartItem_24110248 i : items.values()) sum += i.getSubtotal();
        return sum;
    }
}
