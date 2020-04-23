package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.ShoppingCart;

import java.util.List;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart get(Long id);

    List<ShoppingCart> getAll();

    ShoppingCart update(ShoppingCart product);

    boolean delete(Long id);
}
