package org.artembogomolova.demo.webapp.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  public static final String LOGIN_URL = "/login";
  private static final String LOGOUT_URL = "/logout";
  private static final String[] UNSECURED_RESOURCES = {"/webjars/**", "/css/**", "/resources/**"};
  private static final String[] SECURED_RESOURCES = {"/api/**"};

  private final AuthenticationSuccessHandler loginSuccessfulHandler;
  private final AuthenticationFailureHandler loginFailureHandler;
  private final LogoutSuccessHandler logoutSuccessHandler;
  private final LogoutHandler logoutHandler;
  private final UserDetailsService userDetailsService;

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http)
      throws
      Exception {
    http.authorizeRequests()
        /* api methods need authorization*/
        .antMatchers(SECURED_RESOURCES)
        .authenticated()
        /*but unsecured resources(css, static resources) don't need*/
        .antMatchers(UNSECURED_RESOURCES)
        .permitAll()
        .and()
            /*login page with failure callback*/
            .formLogin()
            .loginPage(LOGIN_URL)
            .permitAll()
            .successHandler(loginSuccessfulHandler)
            .failureHandler(loginFailureHandler)
            .and()
            /*logout page*/
            .logout()
            .permitAll()
            .and()
            .logout()
            .logoutUrl(LOGOUT_URL)
            .logoutSuccessUrl(LOGIN_URL)
            .logoutSuccessHandler(logoutSuccessHandler)
            .invalidateHttpSession(true)
            .addLogoutHandler(logoutHandler)
            .and()
            /*user service with detail data about logged user*/
            .userDetailsService(userDetailsService)
            .exceptionHandling();
  }

}
