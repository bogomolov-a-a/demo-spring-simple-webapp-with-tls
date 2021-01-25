package org.artembogomolova.demo.webapp.test.context.web

import org.artembogomolova.demo.webapp.test.context.AbstractContextLoadTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@DisplayName("Basically test for data exchange through security channel(HTTPS)")
internal class HttpsProtocolExchangeOnTest(
    @LocalServerPort serverPort: Int
) : AbstractContextLoadTest(serverPort) {
    @Value("\${test.ssl.trusted-keystore-file}")
    lateinit var trustedKeystoreFile: String

    @Value("\${test.ssl.trusted-store-type}")
    lateinit var trustedKeystoreType: String

    @Value("\${test.ssl.trusted-store-password}")
    lateinit var trustedKeystorePassword: String

    @Value("\${test.ssl.trusted-key-password}")
    lateinit var trustedKeyPassword: String


    @Test
    @DisplayName("Validated tls connection established, try to get index page by https with generated server certificate.")
    fun validateTLSConnectionCreatedAndIndexPageGet() {
        val restTemplateUtil = RestTemplateUtil.getInstance()
        checkServerPortAssigned()
        val restTemplate: TestRestTemplate = restTemplateUtil.getSecureRestTemplate(
            trustedKeystoreFile,
            trustedKeystoreType,
            trustedKeystorePassword,
            trustedKeyPassword
        )!!
        log.info("try to get index page in secured connection.")
        val data: ResponseEntity<String> = restTemplate.getForEntity<String>("https://localhost:${serverPort}/", String::class.java)
        val result = data.body
        Assertions.assertEquals(HttpStatus.OK, data.statusCode, result)
        log.info("index page in secured connection successful get!")
    }
}