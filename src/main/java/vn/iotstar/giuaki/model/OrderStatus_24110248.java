package vn.iotstar.giuaki.model;

public enum OrderStatus_24110248 {
    NEW       ("new",        "Đơn hàng mới", "primary",   1),
    CONFIRMED ("confirmed",  "Đã xác nhận",  "info",      2),
    PREPARING ("preparing",  "Chuẩn bị hàng", "warning",  3),
    SHIPPING  ("shipping",   "Vận chuyển",   "secondary", 4),
    DELIVERING("delivering", "Giao hàng",    "dark",      5),
    DELIVERED ("delivered",  "Đã giao",      "success",   6),
    CANCELLED ("cancelled",  "Đơn hàng hủy", "danger",    0),
    RETURNED  ("returned",   "Đơn hàng hoàn", "danger",   0);

    public static final int LAST_STEP = 6;

    private final String code;
    private final String label;
    private final String badge;
    private final int step;

    OrderStatus_24110248(String code, String label, String badge, int step) {
        this.code = code;
        this.label = label;
        this.badge = badge;
        this.step = step;
    }

    public String getCode() { return code; }
    public String getLabel() { return label; }
    public String getBadge() { return badge; }
    public int getStep() { return step; }
    public boolean isAbnormal() { return step == 0; }

    public static OrderStatus_24110248 fromCode(String code) {
        if (code == null) return null;
        String c = code.trim();
        for (OrderStatus_24110248 s : values()) {
            if (s.code.equalsIgnoreCase(c)) return s;
        }
        return null;
    }

    public static OrderStatus_24110248 fromLabel(String label) {
        if (label == null) return null;
        String l = label.trim();
        for (OrderStatus_24110248 s : values()) {
            if (s.label.equalsIgnoreCase(l)) return s;
        }
        return null;
    }
}
