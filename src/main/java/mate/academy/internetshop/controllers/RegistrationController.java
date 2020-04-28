package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
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
        String name = req.getParameter("name");
        String pwd = req.getParameter("pwd");
        String repeatPwd = req.getParameter("repeatPwd");

        if (pwd.equals(repeatPwd)) {
            var user = new User(name, pwd);
            userService.create(user);
            resp.sendRedirect(req.getContextPath() + "/");
        } else {
            req.setAttribute("message", "Passwords don't match, try again");
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }

    }
}
