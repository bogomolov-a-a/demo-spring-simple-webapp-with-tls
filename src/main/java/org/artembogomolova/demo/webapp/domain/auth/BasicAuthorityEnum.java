package org.artembogomolova.demo.webapp.domain.auth;

import java.util.Locale;
import org.artembogomolova.demo.webapp.domain.core.IdentifiedEntity;

public enum BasicAuthorityEnum {
  BAT_CREATE("create"),
  BAT_UPDATE("update"),
  BAT_READ("read"),
  BAT_DELETE("delete"),
  BAT_GRANT("grant"),
  BAT_REVOKE("revoke");
  private String name;
  private static final String AUTHORITY_FORMAT = "%s:%s";

  BasicAuthorityEnum(String name) {
    this.name = name;
  }

  static String getAuthorityByEntityAndAction(Class<? extends IdentifiedEntity> entityClass, String action) {
    String entityName = entityClass.getSimpleName().toLowerCase(Locale.ENGLISH);
    return String.format(BasicAuthorityEnum.AUTHORITY_FORMAT, entityName, action.toLowerCase(Locale.ENGLISH));
  }
}
