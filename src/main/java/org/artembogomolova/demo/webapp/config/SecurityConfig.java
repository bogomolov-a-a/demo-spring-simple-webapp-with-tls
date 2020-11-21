package org.artembogomolova.demo.webapp.config;

import lombok.RequiredArgsConstructor;
import org.artembogomolova.demo.webapp.controller.PublicController;
import org.artembogomolova.demo.webapp.domain.auth.PredefinedUserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ComponentScan(value = {"org.artembogomolova.demo.webapp.security", "org.artembogomolova.demo.webapp.controller",
    "org.artembogomolova.demo.webapp.filter"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String LOGOUT_URL = "/logout";
  private static final String[] SECURED_RESOURCES = {"/api/**"};

  private final AuthenticationSuccessHandler loginSuccessfulHandler;
  private final AuthenticationFailureHandler loginFailureHandler;
  private final LogoutSuccessHandler logoutSuccessHandler;
  private final LogoutHandler logoutHandler;
  private final UserDetailsService userDetailsService;
  private final AnonymousAuthenticationFilter anonymousAuthenticationFilterImpl;

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http)
      throws
      Exception {
    configureLoginProcess(http);
    configureLogoutProcess(http);
    configurePublicResources(http);
  }

  private void configurePublicResources(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .antMatchers(PublicController.getUnsecuredResources())
        .hasAnyAuthority(PredefinedUserRole.GUEST.getPrivilegesAsArray());
    /*For guest main page*/
    http.addFilterAt(anonymousAuthenticationFilterImpl, AnonymousAuthenticationFilter.class);
  }

  private void configureLogoutProcess(HttpSecurity http) throws Exception {
    http.logout()
        .logoutUrl(LOGOUT_URL)
        .logoutSuccessUrl(PublicController.LOGIN_URL)
        .logoutSuccessHandler(logoutSuccessHandler)
        .invalidateHttpSession(true)
        .addLogoutHandler(logoutHandler);
  }

  /**
   * login page with failure callback
   */
  private void configureLoginProcess(HttpSecurity http) throws Exception {
    http.formLogin()
        .loginPage(PublicController.LOGIN_URL)
        .successHandler(loginSuccessfulHandler)
        .failureHandler(loginFailureHandler);
  }

}
