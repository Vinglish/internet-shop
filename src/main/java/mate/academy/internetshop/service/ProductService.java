package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.model.Product;

public interface ProductService {
    Product create(Product product);

    Optional<Product> get(Long id);

    List<Product> getAll();

    Product update(Product product);

    void delete(Long id);
}
