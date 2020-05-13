package mate.academy.internetshop;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

import java.math.BigDecimal;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        UserService userService = (UserService) injector.getInstance(UserService.class);
        User user = userService.create(new User(12L, "login", "password"));
        System.out.println(user.toString());
        user.setPassword("new password");
        userService.update(user);
        System.out.println(user.toString());

        ShoppingCartService shoppingCartService
                = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart shoppingCart = new ShoppingCart(12L);
        Product table = new Product("table", new BigDecimal(12));
        Product chair = new Product("chair", new BigDecimal(14));
        shoppingCartService.addProduct(shoppingCart, table);
        shoppingCartService.addProduct(shoppingCart, chair);
        System.out.println("--------------------------------------------");
        shoppingCartService.getAllProducts(shoppingCart)
                .forEach(p -> System.out.println(p.toString()));

        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        orderService.completeOrder(shoppingCartService.getAllProducts(shoppingCart), 12L);
        orderService.getUserOrders(user).forEach(order -> System.out.println(order.toString()));
    }
}
