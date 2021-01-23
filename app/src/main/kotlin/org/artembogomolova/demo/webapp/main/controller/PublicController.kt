package org.artembogomolova.demo.webapp.main.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class PublicController {
    @get:GetMapping(*[ROOT_URL, INDEX_PAGE_URL])
    val indexPage: String
        get() = PUBLIC_PAGE_TEMPLATE_PREFIX + INDEX_PAGE_URL

    @get:GetMapping(LOGIN_URL)
    val loginUrl: String
        get() = PUBLIC_PAGE_TEMPLATE_PREFIX + LOGIN_URL

    companion object {
        private const val ROOT_URL = "/"
        private const val INDEX_PAGE_URL = "/index"
        const val LOGIN_URL = "/login"
        const val TEMPLATE_RESOURCES_URL = "/templates/**"
        const val CSS_RESOURCES_URL = "/css/**"
        const val WEBJARS_RESOURCES_URL = "/webjars/**"
        private const val PUBLIC_PAGE_TEMPLATE_PREFIX = "fragments/public"
        private val UNSECURED_RESOURCES = arrayOf( /*public resources*/
            ROOT_URL, INDEX_PAGE_URL,  /*resources*/
            TEMPLATE_RESOURCES_URL, CSS_RESOURCES_URL, WEBJARS_RESOURCES_URL
        )
        val unsecuredResources: Array<String?>
            get() = UNSECURED_RESOURCES.copyOf(UNSECURED_RESOURCES.size)
    }
}