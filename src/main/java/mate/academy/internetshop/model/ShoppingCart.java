package mate.academy.internetshop.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private Long userId;
    private List<Product> products = new ArrayList<>();

    public ShoppingCart() {
    }

    public ShoppingCart(Long userId) {
        this.userId = userId;
    }

    public ShoppingCart(Long id, Long userId, List<Product> products) {
        this(userId);
        this.id = id;
        this.products = products;
    }

    @Override
    public String toString() {
        return "ShoppingCart{"
                + "id=" + id
                + ", userId=" + userId
                + ", products=" + products
                + '}';
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
