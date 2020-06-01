package mate.academy.internetshop.web.filters;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Role;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebFilter(filterName = "authorization")
public class AuthorizationFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(AuthorizationFilter.class);
    private static final Injector INJECTOR = Injector.getInstance("mate.academy.internetshop");
    private static final String USER_ID = "user-id";
    private final UserService userService = (UserService) INJECTOR.getInstance(UserService.class);
    private final Map<String, List<Role.RoleName>> protectedUrls = new HashMap<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        protectedUrls.put("/users/get-all", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/products/add", List.of(Role.RoleName.USER));
        protectedUrls.put("/products/change", List.of(Role.RoleName.ADMIN));
        protectedUrls.put("/shopping-cart/display", List.of(Role.RoleName.USER));
        protectedUrls.put("/orders/get-all", List.of(Role.RoleName.USER));
        protectedUrls.put("/orders/get-order", List.of(Role.RoleName.USER));
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        String url = req.getServletPath();
        if (protectedUrls.get(url) == null) {
            filterChain.doFilter(req, resp);
            return;
        }
        Long userId = (Long) req.getSession().getAttribute(USER_ID);
        if (userId == null || userService.get(userId) == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        User user = userService.get(userId);
        if (isAuthorizes(user, protectedUrls.get(url))) {
            filterChain.doFilter(req, resp);
        } else {
            LOGGER.warn("User with id: " + user.getId() + " tried to use url" + url);
            req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, resp);
        }
    }

    @Override
    public void destroy() {
    }

    private boolean isAuthorizes(User user, List<Role.RoleName> authorizedRoles) {
        for (Role.RoleName role : authorizedRoles) {
            for (Role userRole : user.getRoles()) {
                if (role.equals(userRole.getRoleName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
