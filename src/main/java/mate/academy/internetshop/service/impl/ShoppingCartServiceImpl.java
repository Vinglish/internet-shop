package mate.academy.internetshop.service.impl;

import java.util.List;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.service.ShoppingCartService;


@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Inject
    private ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        if (shoppingCartDao.get(shoppingCart.getId()).isEmpty()) {
            shoppingCartDao.create(shoppingCart).getProducts().add(product);
        } else {
            shoppingCart.getProducts().add(product);
            shoppingCartDao.update(shoppingCart);
        }
        return shoppingCart;
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        if (shoppingCart.getProducts().remove(product)) {
            shoppingCartDao.update(shoppingCart);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().clear();
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getAll().stream()
                .filter(element -> element.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<Product> getAllProducts(ShoppingCart shoppingCart) {
        return shoppingCart.getProducts();
    }
}
