package org.artembogomolova.demo.webapp.controller;

import java.util.Arrays;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

  private static final String ROOT_URL = "/";
  private static final String INDEX_PAGE_URL = "/index";
  public static final String LOGIN_URL = "/login";
  public static final String TEMPLATE_RESOURCES_URL = "/templates/**";
  public static final String CSS_RESOURCES_URL = "/css/**";
  public static final String WEBJARS_RESOURCES_URL = "/webjars/**";
  private static final String PUBLIC_PAGE_TEMPLATE_PREFIX = "fragments/public";
  private static final String[] UNSECURED_RESOURCES = {
      /*public resources*/
      ROOT_URL, INDEX_PAGE_URL,
      /*resources*/
      TEMPLATE_RESOURCES_URL, CSS_RESOURCES_URL, WEBJARS_RESOURCES_URL};

  public static String[] getUnsecuredResources() {
    return Arrays.copyOf(UNSECURED_RESOURCES, UNSECURED_RESOURCES.length);
  }

  @GetMapping({ROOT_URL, INDEX_PAGE_URL})
  public String getIndexPage() {
    return PUBLIC_PAGE_TEMPLATE_PREFIX + INDEX_PAGE_URL;
  }

  @GetMapping(LOGIN_URL)
  public String getLoginUrl() {
    return PUBLIC_PAGE_TEMPLATE_PREFIX + LOGIN_URL;
  }
}
