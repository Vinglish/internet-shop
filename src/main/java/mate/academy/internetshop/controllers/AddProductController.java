package mate.academy.internetshop.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.ProductService;
import mate.academy.internetshop.util.HashUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/products/add")
public class AddProductController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(HashUtil.class);
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private final ProductService productService
            = (ProductService) INJECTOR.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/products/add.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        productService.create(new Product(name, new BigDecimal(price)));
        LOGGER.info("Product " + name + " was added");
        resp.sendRedirect(req.getContextPath() + "/products/add");
    }
}
