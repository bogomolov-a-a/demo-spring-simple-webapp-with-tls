package org.artembogomolova.demo.webapp.main.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class PublicController {
    @GetMapping(*[ROOT_URL, INDEX_PAGE_URL], produces = [MediaType.TEXT_PLAIN_VALUE])
    @ResponseBody
    fun indexPage() = "Hello!"


    companion object {
        private const val ROOT_URL = "/"
        private const val INDEX_PAGE_URL = "/index"
        private val UNSECURED_RESOURCES = arrayOf(
            /*public resources*/
            ROOT_URL, INDEX_PAGE_URL,  /*resources*/
        )
        val unsecuredResources: Array<String?>
            get() = UNSECURED_RESOURCES.copyOf(UNSECURED_RESOURCES.size)
    }
}