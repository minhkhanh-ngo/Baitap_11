package vn.iotstar.giuaki.services.impl;

import vn.iotstar.giuaki.model.Cart_24110248;
import vn.iotstar.giuaki.model.CartItem_24110248;
import vn.iotstar.giuaki.model.Video_24110248;
import vn.iotstar.giuaki.services.ICartService_24110248;
import vn.iotstar.giuaki.services.IVideoService_24110248;

import java.util.ArrayList;
import java.util.List;

public class CartServiceImpl_24110248 implements ICartService_24110248 {
    private final IVideoService_24110248 videoService = new VideoServiceImpl_24110248();

    private static Integer parseQty(String s) {
        if (s == null) return null;
        try {
            return Integer.valueOf(s.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private static boolean sellable(Video_24110248 v) {
        return v != null && v.isActive();
    }

    @Override
    public String add(Cart_24110248 cart, String videoId, String quantityStr) {
        Integer qty = parseQty(quantityStr);
        if (qty == null || qty < 1) return "Số lượng không hợp lệ (phải là số nguyên ≥ 1).";
        if (videoId == null || videoId.trim().isEmpty()) return "Thiếu mã sản phẩm.";

        Video_24110248 v = videoService.getVideoDetail(videoId.trim());
        if (!sellable(v)) return "Sản phẩm không tồn tại hoặc đã ngừng bán.";
        if (v.getStock() <= 0) return "Sản phẩm \"" + v.getTitle() + "\" đã hết hàng.";

        CartItem_24110248 item = cart.get(v.getVideoId());
        int current = (item == null) ? 0 : item.getQuantity();
        int limit = Cart_24110248.maxAllowed(v.getStock());
        // dùng long để tránh tràn số khi cộng dồn
        if ((long) current + qty > limit) {
            return "Chỉ được mua tối đa " + limit + " cái \"" + v.getTitle() + "\" (trong giỏ đã có " + current + ").";
        }

        if (item == null) {
            item = new CartItem_24110248();
            item.setVideoId(v.getVideoId());
            cart.put(item);
        }
        item.setTitle(v.getTitle());
        item.setPoster(v.getPoster());
        item.setPrice(v.getPrice());
        item.setStock(v.getStock());
        item.setQuantity(current + qty);
        return null;
    }

    @Override
    public String update(Cart_24110248 cart, String videoId, String quantityStr) {
        CartItem_24110248 item = (videoId == null) ? null : cart.get(videoId);
        if (item == null) return "Sản phẩm không có trong giỏ hàng.";

        Integer qty = parseQty(quantityStr);
        if (qty == null || qty < 1) {
            return "Số lượng không hợp lệ (tối thiểu 1). Muốn bỏ sản phẩm hãy bấm Xóa.";
        }

        Video_24110248 v = videoService.getVideoDetail(videoId);
        if (!sellable(v) || v.getStock() <= 0) {
            cart.remove(videoId);
            return "Sản phẩm \"" + item.getTitle() + "\" đã hết hàng hoặc ngừng bán nên được gỡ khỏi giỏ.";
        }
        int limit = Cart_24110248.maxAllowed(v.getStock());
        item.setPrice(v.getPrice());
        item.setStock(v.getStock());
        if (qty > limit) {
            item.setQuantity(Math.min(item.getQuantity(), limit));
            return "Chỉ được mua tối đa " + limit + " cái \"" + v.getTitle() + "\".";
        }
        item.setQuantity(qty);
        return null;
    }

    @Override
    public void remove(Cart_24110248 cart, String videoId) {
        if (videoId != null) cart.remove(videoId);
    }

    @Override
    public void clear(Cart_24110248 cart) {
        cart.clear();
    }

    @Override
    public List<String> refresh(Cart_24110248 cart) {
        List<String> warnings = new ArrayList<>();
        for (CartItem_24110248 item : cart.getItems()) {
            Video_24110248 v = videoService.getVideoDetail(item.getVideoId());
            if (!sellable(v) || v.getStock() <= 0) {
                cart.remove(item.getVideoId());
                warnings.add("\"" + item.getTitle() + "\" đã hết hàng hoặc ngừng bán nên được gỡ khỏi giỏ.");
                continue;
            }
            int limit = Cart_24110248.maxAllowed(v.getStock());
            if (item.getQuantity() > limit) {
                item.setQuantity(limit);
                warnings.add("Số lượng \"" + v.getTitle() + "\" được điều chỉnh về " + limit + " do giới hạn tồn kho.");
            }
            if (item.getPrice() != v.getPrice()) {
                warnings.add("Giá \"" + v.getTitle() + "\" đã thay đổi.");
            }
            item.setTitle(v.getTitle());
            item.setPoster(v.getPoster());
            item.setPrice(v.getPrice());
            item.setStock(v.getStock());
        }
        return warnings;
    }
}
