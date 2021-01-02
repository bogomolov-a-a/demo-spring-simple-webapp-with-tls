package org.artembogomolova.demo.webapp.filter;

import javax.servlet.http.HttpServletRequest;
import org.artembogomolova.demo.webapp.dao.service.UserRepoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class AnonymousAuthenticationFilterImpl extends AnonymousAuthenticationFilter {

  private transient final UserRepoService userRepoService;

  @Autowired
  public AnonymousAuthenticationFilterImpl(UserRepoService userRepoService) {
    super("anonymous");
    this.userRepoService = userRepoService;
  }

  @Override
  protected Authentication createAuthentication(HttpServletRequest request) {
    return userRepoService.getGuestUserToken();
  }
}
