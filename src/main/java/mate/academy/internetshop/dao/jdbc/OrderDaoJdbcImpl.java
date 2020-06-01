package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public Order create(Order order) {
        String query = "INSERT INTO orders (user_id) VALUES (?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, order.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                order.setId(resultSet.getLong(1));
            }
            addProductsToOrder(order);
            return order;
        } catch (SQLException e) {
            throw new DataProcessingException("Create order is failed", e);
        }
    }

    @Override
    public Optional<Order> get(Long id) {
        String query = "SELECT * from orders where order_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getOrderFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Getting of order with id="
                    + id + " is failed", e);
        }
    }

    @Override
    public List<Order> getAll() {
        String query = "SELECT * FROM orders;";
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderList.add(getOrderFromResultSet(resultSet));
            }
            return orderList;
        } catch (SQLException e) {
            throw new DataProcessingException("Get all orders are failed", e);
        }
    }

    @Override
    public Order update(Order order) {
        deleteProductsFromOrder(order.getId());
        addProductsToOrder(order);
        return order;
    }

    @Override
    public boolean delete(Long orderId) {
        String query = "DELETE FROM orders WHERE order_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            deleteProductsFromOrder(orderId);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Delete order is failed", e);
        }
    }

    private void addProductsToOrder(Order order) {
        String query = "INSERT INTO orders_products(order_id, product_id) "
                + "VALUES(?,?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Product product : order.getProducts()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, order.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Add products are failed", e);
        }
    }

    private void deleteProductsFromOrder(Long orderId) {
        String query = "DELETE FROM orders_products WHERE order_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Delete products are failed", e);
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        return new Order(resultSet.getLong("order_id"),
                getProductsFromOrder(resultSet.getLong("order_id")),
                resultSet.getLong("user_id"));
    }

    private List<Product> getProductsFromOrder(Long orderId) {
        String query = "SELECT products.product_id, products.name, products.price "
                + "FROM products "
                + "INNER JOIN orders_products "
                + "ON products.product_id = orders_products.product_id "
                + "WHERE orders_products.order_id=?;";
        List<Product> productList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, orderId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                var product = new Product(resultSet.getLong("product_id"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price"));
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            throw new DataProcessingException("Get products from order are failed", e);
        }
    }

    @Override
    public List<Order> getUserOrders(User user) {
        String query = "SELECT * FROM orders WHERE order_id=?";
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orderList.add(getOrderFromResultSet(resultSet));
            }
            return orderList;
        } catch (SQLException e) {
            throw new DataProcessingException("Get user order are failed", e);
        }
    }
}
