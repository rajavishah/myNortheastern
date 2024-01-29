package com.example.unimanagement.config;
//
import com.example.unimanagement.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    // config

//}
//@Configuration
//@EnableWebSecurity
//public class MyConfig extends WebSecurityConfiguration {

    @Bean
    public UserDetailsService getUserDetailService(){
        return new UserDetailServiceImpl();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setUserDetailsService(this.getUserDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

//    Configure Method
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(authenticationProvider());
//    }

//        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authorizeRequests ->
                            authorizeRequests
                                    .dispatcherTypeMatchers(HttpMethod.valueOf("/admin/**")).hasRole("ADMIN")
                                    .dispatcherTypeMatchers(HttpMethod.valueOf("/student/**")).hasRole("STUDENT")
                                    .dispatcherTypeMatchers(HttpMethod.valueOf("/faculty/**")).hasRole("FACULTY")
                                    .dispatcherTypeMatchers(HttpMethod.valueOf("/**")).permitAll()
                    )
                    .formLogin(formLogin ->
                            formLogin
                                    .loginPage("/signin")
                                    .loginProcessingUrl("/dologin")
                                    .defaultSuccessUrl("/user/index")
                    )
                    .csrf(AbstractHttpConfigurer::disable);

    }

}
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
//    private static List<String> allowedURLs = List.of("/unimanagement", "/login", "/logout", "/unimanagement/signup", "/register");
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
//
//        User user = (User) request.getSession().getAttribute("user");
//        String requestURI = request.getRequestURI();
//        System.out.println("Request URI in filter: " + requestURI);
//        if(!allowedURLs.contains(requestURI)){
//            if(user == null){
//                System.out.println("In filter: Redirecting to login page");
//                response.sendRedirect("/unimanagement");
//                return;
//            }
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//}