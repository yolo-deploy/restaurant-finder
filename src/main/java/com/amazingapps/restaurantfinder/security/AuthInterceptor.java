package com.amazingapps.restaurantfinder.security;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptor for authenticating requests using JWT tokens.
 * Extracts user information from token and sets it in ThreadLocal.
 */
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuthInterceptor implements HandlerInterceptor {

    private final ThreadLocal<String> users = new ThreadLocal<>();

    private final TokenInteract tokenInteract;

    /**
     * Checks JWT token validity and sets user in ThreadLocal before handling request.
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {

        String token = tokenInteract.getToken(request);
        if (tokenInteract.validateToken(token)) {
            String user = tokenInteract.getUser(token);
            if (StringUtils.isNotBlank(user)) {
                users.set(user);
                return true;
            }
        }
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        return false;
    }

    /**
     * Returns the username extracted from the JWT token.
     * @return username string
     */
    public String getUserName() {
        return users.get();
    }

    /**
     * Removes user information from ThreadLocal after request is handled.
     */
    @Override
    public void postHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                           @NonNull Object handler, ModelAndView modelAndView) {
        users.remove();
    }
}