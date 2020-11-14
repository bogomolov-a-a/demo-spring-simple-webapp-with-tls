package org.artembogomolova.demo.webapp.model.auth;

import java.util.Set;
import lombok.Getter;
import org.artembogomolova.demo.webapp.model.business.Action;
import org.artembogomolova.demo.webapp.model.business.Category;
import org.artembogomolova.demo.webapp.model.business.Good;
import org.artembogomolova.demo.webapp.model.business.Order;
import org.artembogomolova.demo.webapp.model.business.OrderPosition;
import org.artembogomolova.demo.webapp.model.business.Producer;
import org.artembogomolova.demo.webapp.model.business.Ticket;
import org.artembogomolova.demo.webapp.model.core.Person;

/**
 * all users can be edit, delete it profiles, send messages to another user.
 */

public enum PredefinedUserRole {

  /**
   * super user, view all users, grant authorities, revoke authorities, block user...
   */
  ROLE_ADMIN(1L, Set.of(
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
  ROLE_MODERATOR(2L, Set.of(
      /*Person*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_READ.name()),
      /*profile remove*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_DELETE.name()),
      /*User*/
      /*Can read user list*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(User.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(User.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      /*Action*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Action.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Action.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Action.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Action.class, BasicAuthorityEnum.BAT_DELETE.name()),
      /*Order*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Order.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Order.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Order.class, BasicAuthorityEnum.BAT_REVOKE.name()),
      /*OrderPosition*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(OrderPosition.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(OrderPosition.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(OrderPosition.class, BasicAuthorityEnum.BAT_REVOKE.name()),
      /*delete order by user claim*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Order.class, BasicAuthorityEnum.BAT_DELETE.name()),
      /*Goods*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Good.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Good.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Good.class, BasicAuthorityEnum.BAT_REVOKE.name()),
      /*delete good by user claim*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Good.class, BasicAuthorityEnum.BAT_DELETE.name()),
      /*Tickets*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Ticket.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Ticket.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Ticket.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Ticket.class, BasicAuthorityEnum.BAT_REVOKE.name()),
      /*Category*/
      /*By producer claim*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Category.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Category.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Category.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Category.class, BasicAuthorityEnum.BAT_DELETE.name()),
      /*Producer*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Producer.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Producer.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      /*By producer claim*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Producer.class, BasicAuthorityEnum.BAT_DELETE.name())
  )),
  /**
   * can be create order, pay order, view all orders...
   */
  ROLE_CUSTOMER(3L, Set.of(
      /*Person*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_READ.name()),
      /*profile remove*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_DELETE.name()),
      /*Action*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Action.class, BasicAuthorityEnum.BAT_READ.name()),
      /*Category*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Category.class, BasicAuthorityEnum.BAT_READ.name()),
      /*Good*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Good.class, BasicAuthorityEnum.BAT_READ.name()),
      /*Order*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Order.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Order.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Order.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      /*revoke claim*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Order.class, BasicAuthorityEnum.BAT_REVOKE.name()),
      /*OrderPosition*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(OrderPosition.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(OrderPosition.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(OrderPosition.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(OrderPosition.class, BasicAuthorityEnum.BAT_DELETE.name()),
      /*Tickets*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Ticket.class, BasicAuthorityEnum.BAT_READ.name()),
      /*Producer*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Producer.class, BasicAuthorityEnum.BAT_READ.name())
  )),
  /**
   * create good request, view it good status...
   */
  ROLE_PRODUCER(4L, Set.of(
      /*Person*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_READ.name()),
      /*profile remove*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_DELETE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Person.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      /*Action*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Action.class, BasicAuthorityEnum.BAT_READ.name()),
      /*Good*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Good.class, BasicAuthorityEnum.BAT_READ.name()),
      /*Category*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Category.class, BasicAuthorityEnum.BAT_READ.name()),
      /*Producer*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Producer.class, BasicAuthorityEnum.BAT_CREATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Producer.class, BasicAuthorityEnum.BAT_UPDATE.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Producer.class, BasicAuthorityEnum.BAT_READ.name()),
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Producer.class, BasicAuthorityEnum.BAT_REVOKE.name())
  )),
  /**
   * guest can view categories,providers,goods.
   */
  ROLE_GUEST(3L, Set.of(
      /*Action*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Action.class, BasicAuthorityEnum.BAT_READ.name()),
      /*Category*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Category.class, BasicAuthorityEnum.BAT_READ.name()),
      /*Good*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Good.class, BasicAuthorityEnum.BAT_READ.name()),
      /*Producer*/
      BasicAuthorityEnum.getAuthorityByEntityAndAction(Producer.class, BasicAuthorityEnum.BAT_READ.name())
  ));
  @Getter
  private Long id;
  @Getter
  private Set<String> privileges;

  PredefinedUserRole(Long id, Set<String> privileges) {
    this.id = id;
    this.privileges = privileges;
  }
}
