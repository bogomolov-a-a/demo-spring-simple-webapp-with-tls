package org.artembogomolova.demo.webapp.main.domain.auth

import org.artembogomolova.demo.webapp.main.domain.core.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.domain.core.Person
import java.util.*

enum class BasicAuthorityEnum(val authorityName: String) {
    BAT_CREATE("create"),
    BAT_UPDATE("update"),
    BAT_READ("read"),
    BAT_DELETE("delete"),
    BAT_GRANT("grant"),
    BAT_REVOKE("revoke"),

    /*GUEST ANONYMOUS ACCESS*/
    BAT_ANONYMOUS("anonymous");

    companion object {
        private const val AUTHORITY_FORMAT = "%s:%s"

        @JvmStatic
        fun getAuthorityByEntityAndAction(entityClass: Class<out IdentifiedEntity<*>>, action: String): String {
            val entityName = entityClass.simpleName.toLowerCase(Locale.ENGLISH)
            return String.format(AUTHORITY_FORMAT, entityName, action.toLowerCase(Locale.ENGLISH))
        }
    }
}

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
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_CREATE.name),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_UPDATE.name),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_READ.name),  /*profile remove*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_DELETE.name
            ),  /*Authority*/ /*grant and revoke any authorities from user role*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Authority::class.java, BasicAuthorityEnum.BAT_GRANT.name),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Authority::class.java, BasicAuthorityEnum.BAT_REVOKE.name),  /*User*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_UPDATE.name),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_READ.name),  /*By user claim*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_DELETE.name),  /*Block User,by user claim*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_REVOKE.name)
        )
    ),

    /**
     * control orders and goods, view all orders, view all good request, good storage status...
     */
    MODERATOR(
        2L, setOf( /*Person*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_CREATE.name),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_UPDATE.name),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_READ.name),  /*profile remove*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_DELETE.name),  /*User*/ /*Can read user list*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_READ.name),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(User::class.java, BasicAuthorityEnum.BAT_UPDATE.name)
        )
    ),

    /**
     * can be create order, pay order, view all orders...
     */
    CUSTOMER(
        3L, setOf( /*Person*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_CREATE.name),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_UPDATE.name),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_READ.name),  /*profile remove*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_DELETE.name)
        )
    ),

    /**
     * create good request, view it good status...
     */
    PRODUCER(
        4L, setOf( /*Person*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_CREATE.name),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_READ.name),  /*profile remove*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_DELETE.name),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(Person::class.java, BasicAuthorityEnum.BAT_UPDATE.name)
        )
    ),

    /**
     * guest can view categories,providers,goods.
     */
    GUEST(5L, setOf(BasicAuthorityEnum.BAT_ANONYMOUS.authorityName));

    val privilegesAsArray: Array<String>
        get() = privileges.toTypedArray()
}