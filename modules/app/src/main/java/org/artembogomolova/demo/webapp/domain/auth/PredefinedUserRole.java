package org.artembogomolova.demo.webapp.domain.auth;

import java.util.Set;
import lombok.Getter;
import org.artembogomolova.demo.webapp.domain.core.Person;

/**
 * all users can be edit, delete it profiles, send messages to another user.
 */

public enum PredefinedUserRole {

  /**
   * super user, view all users, grant authorities, revoke authorities, block user...
   */
  ADMIN(1L, Set.of(
      /*Person*/
      /*profile authorities*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_READ.name()),
      /*profile remove*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_DELETE.name()),
      /*Authority*/
      /*grant and revoke any authorities from user role*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Authority.class, BasicAuthorityEnum.BAT_GRANT.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Authority.class, BasicAuthorityEnum.BAT_REVOKE.name()),
      /*User*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(User.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(User.class, BasicAuthorityEnum.BAT_READ.name()),
      /*By user claim*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(User.class, BasicAuthorityEnum.BAT_DELETE.name()),
      /*Block User,by user claim*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(User.class, BasicAuthorityEnum.BAT_REVOKE.name())
  )),
  /**
   * control orders and goods, view all orders, view all good request, good storage status...
   */
  MODERATOR(2L, Set.of(
      /*Person*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_READ.name()),
      /*profile remove*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_DELETE.name()),
      /*User*/
      /*Can read user list*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(User.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(User.class, BasicAuthorityEnum.BAT_UPDATE.name())

  )),
  /**
   * can be create order, pay order, view all orders...
   */
  CUSTOMER(3L, Set.of(
      /*Person*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_READ.name()),
      /*profile remove*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_DELETE.name())

  )),
  /**
   * create good request, view it good status...
   */
  PRODUCER(4L, Set.of(
      /*Person*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_READ.name()),
      /*profile remove*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_DELETE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_UPDATE.name())
  )),
  /**
   * guest can view categories,providers,goods.
   */
  GUEST(5L, Set.of());
  @Getter
  private Long id;
  @Getter
  private Set<String> privileges;

  PredefinedUserRole(Long id, Set<String> privileges) {
    this.id = id;
    this.privileges = privileges;
  }

  public String[] getPrivilegesAsArray() {
    return privileges.toArray(new String[0]);
  }
}
