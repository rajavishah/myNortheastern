//package com.example.unimanagement.config;
//
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.core.userdetails.User.withUsername;
//
////@Configuration
////@EnableWebSecurity
////@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
////public class SecurityConfig {
////
////    @Bean
////    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////
////        manager.createUser(withUsername("admin")
////                .password(bCryptPasswordEncoder.encode("adminPass"))
////                .roles("USER", "ADMIN")
////                .build());
////
////        return manager;
////    }
////
////    @Bean
////    public BCryptPasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////
//////    @Override
//////    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//////        auth.authenticationProvider(authenticationProvider());
//////    }
////
//////    @Override
//////    protected void configure(SecurityFilterChain serfilter) throws Exception {
//////        serfilter.authorizeHttpRequests(authorizeRequests ->
//////                        authorizeRequests
//////                                .requestMatchers("/admin/**").hasRole("ADMIN")
//////                                .mvcMatchers("/student/**").hasRole("STUDENT")
//////                                .mvcMatchers("/faculty/**").hasRole("FACULTY")
//////                                .anyRequest().permitAll()
//////                )
//////                .formLogin(formLogin ->
//////                        formLogin
//////                                .loginPage("/signin")
//////                                .loginProcessingUrl("/dologin")
//////                                .defaultSuccessUrl("/user/index")
//////                )
//////                .csrf().disable();
//////    }
////@Bean
////public SecurityFilterChain filterChain(HttpServletRequest http) throws Exception {
////
////        http.matches((matchers) -> matchers
////                                    .requestMatchers("/admin/**").hasRole("ADMIN")
////                                 .matches("/student/**").hasRole("STUDENT")
////                                .matches("/faculty/**").hasRole("FACULTY")
////                                .anyRequest().permitAll()
////            )
////            .formLogin(formLogin -> formLogin
////                    .loginPage("/signin"))
////            .logout(logout -> logout.logoutUrl("/signout").permitAll());
////
////
////    return http.build();
////}
////}
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Autowired
//    private UserDetailsService userDetailsService; // Autowire your implementation of UserDetailsService
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests()
//                .antMatchers("/login", "/signup").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/signin")
//                .defaultSuccessUrl("/user/index")
//                .permitAll()
//                .and()
//                .logout()
//                .logoutUrl("/logout")
//                .logoutSuccessUrl("/login?logout")
//                .permitAll();
//    }
//}
