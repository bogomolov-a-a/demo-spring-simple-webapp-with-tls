package org.artembogomolova.demo.webapp.test.context.web

import org.apache.http.conn.ssl.NoopHostnameVerifier
import org.apache.http.conn.ssl.SSLConnectionSocketFactory
import org.apache.http.conn.ssl.TrustSelfSignedStrategy
import org.apache.http.impl.client.HttpClients
import org.apache.http.ssl.SSLContextBuilder
import org.artembogomolova.demo.webapp.main.util.getLogger
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.core.io.FileSystemResource
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import java.io.File
import java.security.KeyStore
import java.util.Objects
import java.util.function.Supplier

class RestTemplateUtil {
    companion object {
        private var INSTANCE: RestTemplateUtil? = null
        fun getInstance(): RestTemplateUtil {
            if (INSTANCE == null) {
                INSTANCE = RestTemplateUtil()
            }
            return INSTANCE as RestTemplateUtil
        }
    }

    private var restTemplate: TestRestTemplate? = null

    fun getSecureRestTemplate(keyStoreFile: String?, trustedKeystoreType: String?, keyStorePass: String?, keyPass: String?): TestRestTemplate? {
        if (restTemplate != null) {
            return restTemplate
        }
        val builder = RestTemplateBuilder()
        restTemplate = TestRestTemplate(builder, null, null, TestRestTemplate.HttpClientOption.ENABLE_REDIRECTS)
        builder.requestFactory(ClientHttpRequestFactorySupplier(keyStoreFile!!, trustedKeystoreType!!, keyStorePass!!, keyPass!!))
            .buildRequestFactory()
        return restTemplate
    }


    private class ClientHttpRequestFactorySupplier(
        private val keyStoreFile: String,
        private val trustedKeystoreType: String,
        private val keyStorePass: String,
        private val keyPass: String
    ) : Supplier<ClientHttpRequestFactory> {

        override fun get(): ClientHttpRequestFactory {
            return buildHttpRequestFactory()
        }

        private fun buildHttpRequestFactory(

        ): ClientHttpRequestFactory {
            val log = getLogger(RestTemplateUtil::class.java)
            val socketFactory: SSLConnectionSocketFactory = try {
                val keyStore: KeyStore = KeyStore.getInstance(trustedKeystoreType)
                val keyFile = File(keyStoreFile)
                val fileSystemResource = FileSystemResource(keyFile)
                fileSystemResource.inputStream.use {
                    keyStore.load(
                        it, Objects.requireNonNull(keyStorePass)
                            .toCharArray()
                    )
                }
                SSLConnectionSocketFactory(
                    SSLContextBuilder().loadTrustMaterial(null, TrustSelfSignedStrategy())
                        .loadKeyMaterial(keyStore, keyPass.toCharArray())
                        .build(),
                    NoopHostnameVerifier.INSTANCE
                )
            } catch (e: Exception) {
                log.debug("SSL keystore exception", e)
                throw RuntimeException(e)
            }
            val httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build()
            return HttpComponentsClientHttpRequestFactory(httpClient)
        }
    }
}