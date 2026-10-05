package vn.iotstar.giuaki.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order_24110248 {
    public static final String PAYMENT_COD = "COD";
    public static final String STATUS_PENDING = "Chờ xác nhận";

    private int orderId;
    private String username;
    private String receiverName;
    private String phone;
    private String address;
    private String note;
    private long totalAmount;
    private String paymentMethod = PAYMENT_COD;
    private String status = STATUS_PENDING;
    private Date createdDate;
    private List<OrderDetail_24110248> details = new ArrayList<>();

    public Order_24110248() {}

    public int getOrderId() { return orderId; }
    public void setOrderId(int orderId) { this.orderId = orderId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public long getTotalAmount() { return totalAmount; }
    public void setTotalAmount(long totalAmount) { this.totalAmount = totalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
    public List<OrderDetail_24110248> getDetails() { return details; }
    public void setDetails(List<OrderDetail_24110248> details) { this.details = details; }
}
