package mate.academy.internetshop.dao.impl;

import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.ShoppingCart;

@Dao
public class ShoppingCartImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        return null;
    }

    @Override
    public ShoppingCart get(Long shoppingCartId) {
        return Storage.shoppingCarts.stream()
                .filter(p -> p.getId().equals(shoppingCartId))
                .findAny()
                .orElseThrow();
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        return null;
    }
}
