package com.khsa.usermanagement.security;

import com.khsa.usermanagement.service.UserService;
import com.khsa.usermanagement.util.CustomPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserService userService;
    private CustomPasswordEncoder customPasswordEncoder;

    /**
     * Creates an instance with the default configuration enabled.
     */
    @Autowired
    public SecurityConfiguration(UserService userService, CustomPasswordEncoder customPasswordEncoder) {
        this.userService = userService;
        this.customPasswordEncoder = customPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**").access("hasAuthority('user')")
                .antMatchers("/analytic/**").access("hasAuthority('admin')")
                .antMatchers("/").permitAll()
                .antMatchers("/user/register").permitAll()
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

//    http.logout().logoutUrl("/my/logout")
//      .logoutSuccessUrl("/my/index")
//      .logoutSuccessHandler(logoutSuccessHandler)
//      .invalidateHttpSession(true)
//      .addLogoutHandler(logoutHandler)
//      .deleteCookies(cookieNamesToClear)
//      .and()
//
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(customPasswordEncoder);
    }

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
