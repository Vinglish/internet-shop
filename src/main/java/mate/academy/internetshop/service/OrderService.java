package mate.academy.internetshop.service;

import java.util.List;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;

public interface OrderService extends GenericService<Order,Long> {
    Order completeOrder(List<Product> products, Long userId);

    List<Order> getUserOrders(User user);
}
