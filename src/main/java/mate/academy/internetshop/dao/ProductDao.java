package mate.academy.internetshop.dao;

import java.util.List;
import mate.academy.internetshop.model.Product;

public interface ProductDao {
    Product create(Product product);

    Product get(Long id);

    List<Product> getAll();

    Product update(Product product);

    boolean delete(Long id);
}
