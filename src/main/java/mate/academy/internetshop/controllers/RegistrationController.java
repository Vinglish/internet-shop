package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;

public class RegistrationController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String pwd = req.getParameter("pwd");
        String repeatPwd = req.getParameter("repeatPwd");

        if (pwd.equals(repeatPwd)) {
            var user = new User(login, pwd);
            user.setRoles(List.of(Role.of("USER")));
            userService.create(user);
            resp.sendRedirect(req.getContextPath() + "/products/add-product");
        } else {
            req.setAttribute("message", "Passwords don't match, try again");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }

    }
}
