package org.artembogomolova.demo.webapp.main.domain.auth

import org.artembogomolova.demo.webapp.main.domain.IdentifiedEntity
import java.util.*

enum class BasicAuthorityEnum(val authorityName: String) {
    BAT_CREATE("create"), BAT_UPDATE("update"), BAT_READ("read"), BAT_DELETE("delete"), BAT_GRANT("grant"), BAT_REVOKE("revoke");

    companion object {
        private const val AUTHORITY_FORMAT = "%s:%s"

        @JvmStatic
        fun getAuthorityByEntityAndAction(entityClass: Class<out IdentifiedEntity?>, action: String): String {
            val entityName = entityClass.simpleName.toLowerCase(Locale.ENGLISH)
            return String.format(AUTHORITY_FORMAT, entityName, action.toLowerCase(Locale.ENGLISH))
        }
    }
}