package mate.academy.internetshop.dao;

import mate.academy.internetshop.model.User;

import java.util.List;

public interface UserDao {
    User create(User product);

    User get(Long id);

    List<User> getAll();

    User update(User product);

    boolean delete(Long id);
}
