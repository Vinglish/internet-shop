package mate.academy.internetshop.model;

import java.util.List;

public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private Long orderId;

    public Long getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
