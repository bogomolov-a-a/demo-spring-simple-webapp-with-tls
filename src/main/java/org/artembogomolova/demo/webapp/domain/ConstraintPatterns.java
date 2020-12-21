package org.artembogomolova.demo.webapp.domain;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ConstraintPatterns {

  public static final String PHONE_PATTERN = "\\+([0-9]{0,3})\\-([0-9]{3})\\-([0-9]{3})\\-([0-9]{2})\\-([0-9]{2})";
}
