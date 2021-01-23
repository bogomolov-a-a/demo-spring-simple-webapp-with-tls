package org.artembogomolova.demo.webapp.main.domain.auth

import org.artembogomolova.demo.webapp.main.domain.auth.BasicAuthorityEnum.Companion.getAuthorityByEntityAndAction
import org.artembogomolova.demo.webapp.main.domain.core.Person

/**
 * all users can be edit, delete it profiles, send messages to another user.
 */
enum class PredefinedUserRole(
    val id: Long,
    val privileges: Set<String>
) {
    /**
     * super user, view all users, grant authorities, revoke authorities, block user...
     */
    ADMIN(
        1L, setOf( /*Person*/ /*profile authorities*/
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_CREATE.name),
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_UPDATE.name),
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_READ.name),  /*profile remove*/
            getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_DELETE.name
            ),  /*Authority*/ /*grant and revoke any authorities from user role*/
            getAuthorityByEntityAndAction(Authority::class.java, BasicAuthorityEnum.BAT_GRANT.name),
            getAuthorityByEntityAndAction(Authority::class.java, BasicAuthorityEnum.BAT_REVOKE.name),  /*User*/
            getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_UPDATE.name),
            getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_READ.name),  /*By user claim*/
            getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_DELETE.name),  /*Block User,by user claim*/
            getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_REVOKE.name)
        )
    ),

    /**
     * control orders and goods, view all orders, view all good request, good storage status...
     */
    MODERATOR(
        2L, setOf( /*Person*/
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_CREATE.name),
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_UPDATE.name),
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_READ.name),  /*profile remove*/
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_DELETE.name),  /*User*/ /*Can read user list*/
            getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_READ.name),
            getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_UPDATE.name)
        )
    ),

    /**
     * can be create order, pay order, view all orders...
     */
    CUSTOMER(
        3L, setOf( /*Person*/
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_CREATE.name),
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_UPDATE.name),
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_READ.name),  /*profile remove*/
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_DELETE.name)
        )
    ),

    /**
     * create good request, view it good status...
     */
    PRODUCER(
        4L, setOf( /*Person*/
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_CREATE.name),
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_READ.name),  /*profile remove*/
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_DELETE.name),
            getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_UPDATE.name)
        )
    ),

    /**
     * guest can view categories,providers,goods.
     */
    GUEST(5L, setOf());

    val privilegesAsArray: Array<String>
        get() = privileges.toTypedArray()
}