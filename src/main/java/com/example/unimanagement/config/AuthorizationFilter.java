//package com.example.unimanagement.config;
//
//import com.example.unimanagement.model.User;
//import jakarta.servlet.*;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//@Order(1)
//public class AuthorizationFilter implements Filter {
//
//    private static List<String> allowedURLs = List.of("/unimanagement", "/unimanagement", "/unimanagement/login", "/unimanagement/logout", "/unimanagement/perform-login", "/unimanagement/signup", "/unimanagement/register");
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        User user = (User) request.getSession().getAttribute("user");
//        String contextPath = request.getContextPath();
//        String requestURI = request.getRequestURI();
//        System.out.println("Request URI in filter: " + requestURI);
//        if(!allowedURLs.contains(requestURI)){
//            if(user == null){
//                System.out.println("In filter: Redirecting to login page");
//                response.sendRedirect(contextPath+"/login");
//                return;
//            }
//
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//}
