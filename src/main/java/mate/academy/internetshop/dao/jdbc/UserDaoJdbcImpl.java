package mate.academy.internetshop.dao.jdbc;

import java.sql.*;
import java.util.*;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.util.ConnectionUtil;

@Dao
public class UserDaoJdbcImpl implements UserDao {
    @Override
    public Optional<User> findByLogin(String login) {
        String query = "SELECT * FROM users WHERE login=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("find user are failed", e);
        }
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO internet_shop.users (login, password) VALUES (?,?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(1));
            }
            addRolesForUser(user);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Creation user is failed", e);
        }
    }

    @Override
    public Optional<User> get(Long id) {
        String query = "SELECT * FROM internet_shop.users WHERE user_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(getUserFromResultSet(resultSet));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new DataProcessingException("User is not exist", e);
        }
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM internet_shop.users";
        List<User> usersList = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                usersList.add(getUserFromResultSet(resultSet));
            }
            return usersList;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't take users list" , e);
        }
    }

    @Override
    public User update(User user) {
        String query = "UPDATE internet_shop.users "
                + "SET name=?, login=?, password=? WHERE user_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getName());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getPassword());
            statement.setLong(4, user.getId());
            statement.executeUpdate();
            deleteRolesFromUser(user);
            addRolesForUser(user);
            return user;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't update user", e);
        }
    }

    @Override
    public boolean delete(Long id) {
        String query = "DELETE FROM internet_shop.users WHERE user_id=?;";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, id);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DataProcessingException("Can't delete user", e);
        }
    }

    private void addRolesForUser(User user) {
        String query = "INSERT INTO internet_shop.users_roles VALUES (?,?);";
        try (Connection connection = ConnectionUtil.getConnection()) {
            for (Role role : user.getRoles()) {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setLong(1, user.getId());
                statement.setLong(2, role.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Problem with add roles" ,e);
        }
    }

    private void deleteRolesFromUser(User user) {
        String query = "DELETE FROM internet_shop.users_roles WHERE user_id=?";
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Problem with delete roles", e);
        }
    }

    private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong("user_id"),
                resultSet.getString("login"),
                resultSet.getString("password"),
                getRoles(resultSet.getLong("user_id")));
    }

    private Set<Role> getRoles (Long userId) {

        String query = "SELECT roles.role_name FROM users_roles INNER JOIN roles "
                + "ON  users_roles.role_id=roles.role_id "
                + "WHERE users_roles.user_id = ?;";
        Set<Role> roleSet = new HashSet<>();
        try (Connection connection = ConnectionUtil.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                roleSet.add(Role.of(resultSet.getString("role_name")));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get roles of user", e);
        }
        return roleSet;
    }
}
