package org.artembogomolova.demo.webapp.security.authrorize.request;

import java.util.Collection;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

@Service
public class AccessDecisionManagerImpl implements AccessDecisionManager {

  @Override
  public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
      throws AccessDeniedException, InsufficientAuthenticationException {

  }

  @Override
  public boolean supports(ConfigAttribute attribute) {
    return true;
  }

  @Override
  public boolean supports(Class<?> clazz) {
    return clazz.equals(FilterInvocation.class);
  }
}