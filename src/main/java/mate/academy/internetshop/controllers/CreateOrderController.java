package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

@WebServlet("/orders/create-order")
public class CreateOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final OrderService orderService = (OrderService) INJECTOR
            .getInstance(OrderService.class);
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService = (ShoppingCartService) INJECTOR
            .getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String userId = req.getParameter("user-id");
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(Long.valueOf(userId));
        User user = userService.get(Long.valueOf(userId));
        orderService.completeOrder(shoppingCart.getProducts(), user);
        resp.sendRedirect(req.getContextPath() + "/orders/get-all-orders");
    }
}
