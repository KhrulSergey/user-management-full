package com.khsa.usermanagement.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
//
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/", "/*/html").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/user/**").permitAll()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .successForwardUrl("/index")
                .defaultSuccessUrl("/homepage", true)
                .and()
                .logout().permitAll()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .cors().disable()
                .csrf().disable();
    }

//    @Autowired
//    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("username").password(passwordEncoder().encode("password")).roles("USER, ADMIN")
//                .and()
//                .withUser("admin").password("password").roles("ADMIN")
//        //                .withUser("user")
//        //                .password(passwordEncoder().encode("password"))
//        //                .roles("USER")
//        //
//        //                .withUser("admin")
//        //                .password(passwordEncoder().encode("admin"))
//        //                .roles("ADMIN")
//        ;
//    }
}
