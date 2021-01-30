package org.artembogomolova.demo.webapp.main.domain.auth

import java.util.Locale
import org.artembogomolova.demo.webapp.main.domain.core.IdentifiedEntity
import org.artembogomolova.demo.webapp.main.domain.core.Person

enum class BasicAuthorityEnum(
    val authorityName: String,
    val description: String
) {
    BAT_CREATE("create", "can create entity"),
    BAT_UPDATE("update", "can update entity"),
    BAT_READ("read", "can read entity"),
    BAT_DELETE("delete", "can delete entity"),
    BAT_GRANT("grant", "can grant authority"),
    BAT_REVOKE("revoke", "can revoke authority"),

    /*GUEST ANONYMOUS ACCESS*/
    BAT_ANONYMOUS("anonymous", "use for anonymous access");

    companion object {
        private const val AUTHORITY_FORMAT = "%s:%s"
        private const val AUTHORITY_DESCRIPTION_FORMAT = "%s '%s'"

        @JvmStatic
        fun getAuthorityByEntityAndAction(entityClass: Class<out IdentifiedEntity<*>>, action: String, description: String): Authority {
            val entityName = entityClass.simpleName.toLowerCase(Locale.ENGLISH)
            return Authority(
                AUTHORITY_FORMAT.format(entityName, action.toLowerCase(Locale.ENGLISH)),
                AUTHORITY_DESCRIPTION_FORMAT.format(entityName, description)
            )
        }
    }
}

/**
 * all users can be edit, delete it profiles, send messages to another user.
 */
enum class PredefinedUserRole(
    val id: Long,
    val description: String,
    val privileges: Set<Authority>
) {
    /**
     * super user, view all users, grant authorities, revoke authorities, block user...
     */
    ADMIN(
        1L,
        "super user, view all users, grant authorities, revoke authorities, block user...",
        setOf( /*Person*/
            /*profile authorities*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_CREATE.name,
                BasicAuthorityEnum.BAT_CREATE.description
            ),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_READ.name,
                BasicAuthorityEnum.BAT_READ.description
            ),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_UPDATE.name,
                BasicAuthorityEnum.BAT_UPDATE.description
            ),
            /*profile remove*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_DELETE.name,
                BasicAuthorityEnum.BAT_DELETE.description
            ),
            /*Authority*/
            /*grant and revoke any authorities from user role*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Authority::class.java,
                BasicAuthorityEnum.BAT_GRANT.name,
                BasicAuthorityEnum.BAT_GRANT.description
            ),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Authority::class.java,
                BasicAuthorityEnum.BAT_REVOKE.name,
                BasicAuthorityEnum.BAT_REVOKE.description
            ),
            /*User*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                User::class.java,
                BasicAuthorityEnum.BAT_CREATE.name,
                BasicAuthorityEnum.BAT_CREATE.description
            ),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                User::class.java,
                BasicAuthorityEnum.BAT_READ.name,
                BasicAuthorityEnum.BAT_READ.description
            ),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                User::class.java,
                BasicAuthorityEnum.BAT_UPDATE.name,
                BasicAuthorityEnum.BAT_UPDATE.description
            ),
            /*By user claim*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                User::class.java,
                BasicAuthorityEnum.BAT_DELETE.name,
                BasicAuthorityEnum.BAT_DELETE.description
            ),
            /*Block User,by user claim*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                User::class.java,
                BasicAuthorityEnum.BAT_REVOKE.name,
                BasicAuthorityEnum.BAT_REVOKE.description
            )
        )
    ),

    /**
     * control other users, other entity status...
     */
    MODERATOR(
        2L,
        "control other users, other entity status...",
        setOf(
            /*Person*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_CREATE.name,
                BasicAuthorityEnum.BAT_CREATE.description
            ),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_READ.name,
                BasicAuthorityEnum.BAT_READ.description
            ),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_UPDATE.name,
                BasicAuthorityEnum.BAT_UPDATE.description
            ),
            /*profile remove*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_DELETE.name,
                BasicAuthorityEnum.BAT_DELETE.description
            ),
            /*User*/
            /*Can read user list*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                User::class.java,
                BasicAuthorityEnum.BAT_READ.name,
                BasicAuthorityEnum.BAT_READ.description
            ),
            /*Can update user profile authorities, block user and other*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                User::class.java,
                BasicAuthorityEnum.BAT_UPDATE.name,
                BasicAuthorityEnum.BAT_UPDATE.description
            )
        )
    ),

    /**
     * can be create order, pay order, view all orders...
     */
    CUSTOMER(
        3L,
        "can be create order, pay order, view all orders...",
        setOf( /*Person*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_CREATE.name,
                BasicAuthorityEnum.BAT_CREATE.description
            ),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_READ.name,
                BasicAuthorityEnum.BAT_READ.description
            ),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_UPDATE.name,
                BasicAuthorityEnum.BAT_UPDATE.description
            ),
            /*self profile remove*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_DELETE.name,
                BasicAuthorityEnum.BAT_DELETE.description
            )
        )
    ),

    /**
     * create good request, view it good status...
     */
    PRODUCER(
        4L,
        "create good request, view it good status...",
        setOf( /*Person*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_CREATE.name,
                BasicAuthorityEnum.BAT_CREATE.description
            ),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_READ.name,
                BasicAuthorityEnum.BAT_READ.description
            ),
            /*self profile remove*/
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_DELETE.name,
                BasicAuthorityEnum.BAT_DELETE.description
            ),
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_UPDATE.name,
                BasicAuthorityEnum.BAT_UPDATE.description
            )
        )
    ),

    /**
     * guest has anonymous access for default page.
     */
    GUEST(
        5L,
        "guest has anonymous access for default page.",
        setOf(
            BasicAuthorityEnum.getAuthorityByEntityAndAction(
                Person::class.java,
                BasicAuthorityEnum.BAT_ANONYMOUS.authorityName,
                BasicAuthorityEnum.BAT_ANONYMOUS.description
            )
        )
    );


    val privilegesAsArray: Array<String>
        @Suppress("UNCHECKED_CAST")
        get() = privileges.map(Authority::name).toTypedArray() as Array<String>

}