package com.klima.projekt1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
//@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

   @Autowired
   private AuthenticationSuccessHandlerExt successHandler;

   @Autowired
   public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
      auth.inMemoryAuthentication()
              .withUser("user").password("user").roles("USER").and()
              .withUser("admin").password("admin").roles("ADMIN");

   }

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http
              .authorizeRequests()
              .antMatchers("/","/js/**","/fonts/**","/css/**","/register", "/contact").permitAll()
              .anyRequest().authenticated()
              .and().formLogin().permitAll().loginPage("/login").failureUrl("/login_error")
              .and().formLogin().successHandler(successHandler)
              .and().logout().logoutUrl("/").permitAll();
   }
}
