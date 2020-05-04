package mate.academy.internetshop.security;

import mate.academy.internetshop.exceptions.AuthenticationException;
import mate.academy.internetshop.model.User;

public interface AuthenticationService {
    User login(String login, String password) throws AuthenticationException;
}
