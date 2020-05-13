package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.ShoppingCartDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class ShoppingCartDaoJdbcImpl implements ShoppingCartDao {
    @Override
    public ShoppingCart create(ShoppingCart shoppingCart) {
        String query = "INSERT INTO internet_shop.shopping_carts (user_id) VALUES (?)";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement
                    = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, shoppingCart.getUserId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                shoppingCart.setId(resultSet.getLong(1));
            }
            return shoppingCart;
        } catch (SQLException e) {
            throw new DataProcessingException("Create shopping cart is failed", e);
        }
    }

    @Override
    public Optional<ShoppingCart> get(Long id) {
        String query = "SELECT * FROM internet_shop.shopping_carts WHERE cart_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getCartFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("Cart does not exist", e);
        }
    }

    @Override
    public List<ShoppingCart> getAll() {
        String query = "SELECT * FROM internet_shop.shopping_carts;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            List<ShoppingCart> cartsList = new ArrayList<>();
            if (resultSet.next()) {
                cartsList.add(getCartFromResultSet(resultSet));
            }
            return cartsList;
        } catch (SQLException e) {
            throw new DataProcessingException("Cart does not exist", e);
        }

    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingCart) {
        deleteProductsFromCart(shoppingCart);
        addProductsToCart(shoppingCart);
        return shoppingCart;
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM shopping_carts WHERE cart_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Delete of shopping cart with id="
                    + id + " is failed", e);
        }
    }

    private void addProductsToCart(ShoppingCart shoppingCart) {
        String query = "INSERT INTO "
                + "internet_shop.shopping_carts_products(cart_id, product_id) "
                + "VALUES(?,?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Product product : shoppingCart.getProducts()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, shoppingCart.getId());
                statement.setLong(2, product.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't add products to shopping cart", e);
        }
    }

    private void deleteProductsFromCart(ShoppingCart shoppingCart) {
        String query = "DELETE FROM internet_shop.shopping_carts_products WHERE cart_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, shoppingCart.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete products from shopping cart", e);
        }
    }

    private ShoppingCart getCartFromResultSet(ResultSet resultSet) throws SQLException {
        return new ShoppingCart(resultSet.getLong("cart_id"),
                resultSet.getLong("user_id"),
                getProductsFromCart(resultSet.getLong("cart_id")));
    }

    private List<Product> getProductsFromCart(Long cartId) {
        String query = "SELECT products.product_id, products.name, products.price "
                + "FROM products "
                + "INNER JOIN shopping_carts_products "
                + "ON products.product_id = shopping_carts_products.product_id "
                + "WHERE shopping_carts_products.cart_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            List<Product> productsList = new ArrayList<>();
            while (resultSet.next()) {
                var product = new Product(resultSet.getLong("product_id"),
                        resultSet.getString("name"),
                        resultSet.getBigDecimal("price"));
                productsList.add(product);
            }
            return productsList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't take products", e);
        }
    }
}
