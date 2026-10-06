package vn.iotstar.giuaki.util;

import vn.iotstar.giuaki.model.Cart_24110248;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class CartStore_24110248 {
    private static final Map<String, Cart_24110248> CARTS = new ConcurrentHashMap<>();

    private CartStore_24110248() {}

    public static Cart_24110248 get(String username) {
        return CARTS.computeIfAbsent(username, k -> new Cart_24110248());
    }
}