package mate.academy.internetshop;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.ShoppingCart;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.service.ShoppingCartService;
import mate.academy.internetshop.service.UserService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        ShoppingCartService shoppingCartService
                = (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        UserService userService = (UserService) injector.getInstance(UserService.class);

        Product table = new Product("table", 23.4);
        Product chair = new Product("chair", 15);
        Product bed = new Product("bed", 50.7);
        Product wardrobe = new Product("wardrobe", 30.1);

        User user = userService.create(new User("Nick", "login", "password"));
        System.out.println(user.toString());
        user.setPassword("new password");
        userService.update(user);
        System.out.println(user.toString());

        ShoppingCart shoppingCart = new ShoppingCart(user);
        shoppingCartService.addProduct(shoppingCart, table);
        shoppingCartService.addProduct(shoppingCart, chair);
        System.out.println("--------------------------------------------");
        shoppingCartService.getAllProducts(shoppingCart)
                .forEach(p -> System.out.println(p.toString()));


        orderService.completeOrder(shoppingCartService.getAllProducts(shoppingCart), user);
        orderService.getUserOrders(user).forEach(order -> System.out.println(order.toString()));


    }
}
