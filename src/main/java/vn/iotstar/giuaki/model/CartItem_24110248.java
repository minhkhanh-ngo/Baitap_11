package vn.iotstar.giuaki.model;

import java.io.Serializable;

/** Một dòng trong giỏ hàng (lưu trong session). */
public class CartItem_24110248 implements Serializable {
    private static final long serialVersionUID = 1L;

    private String videoId;
    private String title;
    private String poster;
    private long price;
    private int quantity;
    private int stock; // tồn kho tại lần đồng bộ gần nhất

    public CartItem_24110248() {}

    /** Số lượng tối đa được phép mua: không vượt quá tồn kho và giới hạn mỗi sản phẩm. */
    public int getMaxQuantity() {
        return Cart_24110248.maxAllowed(stock);
    }

    public long getSubtotal() { return price * quantity; }

    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getPoster() { return poster; }
    public void setPoster(String poster) { this.poster = poster; }
    public long getPrice() { return price; }
    public void setPrice(long price) { this.price = price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
