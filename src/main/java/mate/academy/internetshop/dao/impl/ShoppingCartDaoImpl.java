package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.stream.IntStream;

import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.ShoppingCart;

@Dao
public class ShoppingCartDaoImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        Storage.addShoppingCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public ShoppingCart get(Long id) {
        return Storage.shoppingCarts.stream()
                .filter(shoppingCart -> shoppingCart.getId().equals(id))
                .findAny()
                .orElseThrow();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        IntStream.range(0, Storage.shoppingCarts.size())
                .filter(i -> shoppingCart.getId().equals(Storage.shoppingCarts.get(i).getId()))
                .forEach(i -> Storage.shoppingCarts.set(i, shoppingCart));
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.shoppingCarts.removeIf(value -> value.getId().equals(id));
    }
}
