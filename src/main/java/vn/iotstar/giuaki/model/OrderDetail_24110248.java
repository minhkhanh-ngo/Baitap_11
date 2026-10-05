package vn.iotstar.giuaki.model;

public class OrderDetail_24110248 {
    private String videoId;
    private String title;
    private int quantity;
    private long unitPrice;

    public OrderDetail_24110248() {}

    public long getSubtotal() { return unitPrice * quantity; }

    public String getVideoId() { return videoId; }
    public void setVideoId(String videoId) { this.videoId = videoId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public long getUnitPrice() { return unitPrice; }
    public void setUnitPrice(long unitPrice) { this.unitPrice = unitPrice; }
}
