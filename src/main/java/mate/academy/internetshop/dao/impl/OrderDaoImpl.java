package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.Storage;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public class OrderDaoImpl implements OrderDao {

    @Override
    public Order create(Order order) {
        Storage.addOrders(order);
        return order;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Storage.orders.stream()
                .filter(o -> o.getId().equals(id))
                .findAny();
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

    @Override
    public Order update(Order order) {
        IntStream.range(0, Storage.orders.size())
                .filter(i -> order.getId().equals(Storage.orders.get(i).getId()))
                .forEach(i -> Storage.orders.set(i, order));
        return order;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.orders.removeIf(o -> o.getId().equals(id));
    }

    @Override
    public List<Order> getUserOrders(User user) {
        return Storage.orders.stream()
                .filter(o -> o.getUserId().equals(user.getId()))
                .collect(Collectors.toList());
    }
}
