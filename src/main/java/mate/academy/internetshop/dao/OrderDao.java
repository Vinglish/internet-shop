package mate.academy.internetshop.dao;

import java.util.List;
import mate.academy.internetshop.model.Order;

public interface OrderDao {
    Order create(Order product);

    Order get(Long id);

    List<Order> getAll();

    Order update(Order product);

    boolean delete(Long id);
}
