package com.medical.clinic.config;

import com.medical.clinic.service.ClinicUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new ClinicUserService();
//    }

    @Autowired
    private final ClinicUserService clinicUserService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(clinicUserService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }
    @Autowired
    LoginSuccessHandler loginSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/patient/**").hasAnyRole("PATIENT","ADMIN")
                .antMatchers("/doctor/**").hasAnyRole("DOCTOR","ADMIN")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/login")
                .and()
                .formLogin()
                .successHandler(loginSuccessHandler)
                .usernameParameter("username")
                .loginPage("/login")
                .failureUrl("/login_err")
                .permitAll()
                .and()
                .rememberMe()
                .key("a-very-securty-key")
                .userDetailsService(clinicUserService)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/").permitAll();
        http.csrf().disable();
    }


}

