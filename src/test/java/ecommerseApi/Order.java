package ecommerseApi;

import java.util.List;

public class Order {
    List<OrderDetails> orders;

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }
}
