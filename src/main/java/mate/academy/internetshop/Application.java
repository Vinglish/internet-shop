package mate.academy.internetshop;

import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);

        Product table = new Product("table", 23.4);
        Product chair = new Product("chair", 15);
        Product bed = new Product("bed", 50.7);
        Product wardrobe = new Product("wardrobe", 30.1);

        productService.create(table);
        productService.create(chair);
        productService.create(bed);
        productService.create(wardrobe);

        System.out.println("show all");
        productService.getAll().stream()
                .map(Object::toString).forEach(System.out::println);

        System.out.println("Get chair by id");
        System.out.println(productService.get(chair.getId()).toString());

        System.out.println("Delete product");
        productService.delete(2L);
        productService.getAll().stream()
                .map(Object::toString).forEach(System.out::println);

    }
}
