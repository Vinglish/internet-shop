package mate.academy.internetshop.controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.service.OrderService;

@WebServlet("/orders/get-order")
public class GetOrderController extends HttpServlet {
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final OrderService orderService = (OrderService) INJECTOR
            .getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String orderId = req.getParameter("order-id");
        Order orderValue = orderService.get(Long.valueOf(orderId));
        req.setAttribute("order", orderValue);
        req.getRequestDispatcher("/WEB-INF/views/orders/order.jsp").forward(req, resp);
    }
}

