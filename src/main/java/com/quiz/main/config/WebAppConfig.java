package com.quiz.main.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.quiz.main.model.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Autowired
    private HttpSession session;

    @Override
    public void addInterceptors(@SuppressWarnings("null") InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                User currentUser = (User) session.getAttribute("currentUser");
                if (currentUser == null || !"ROLE_ADMIN".equals(currentUser.getRole())) {
                    response.sendRedirect("/login");
                    return false;
                }
                return true;
            }
        }).addPathPatterns("/admin/**");
    }
}
