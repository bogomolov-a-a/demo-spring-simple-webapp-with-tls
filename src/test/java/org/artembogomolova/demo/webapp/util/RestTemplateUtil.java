package org.artembogomolova.demo.webapp.util;

import java.io.File;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Objects;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestTemplateUtil {

  private TestRestTemplate restTemplate;

  public TestRestTemplate getSecureRestTemplate(String keyStoreFile, String trustedKeystoreType, String keyStorePass, String keyPass) {
    if (restTemplate != null) {
      return restTemplate;
    }
    RestTemplateBuilder builder = new RestTemplateBuilder();
    restTemplate = new TestRestTemplate(builder, null, null, TestRestTemplate.HttpClientOption.ENABLE_REDIRECTS);
    builder.requestFactory(new ClientHttpRequestFactorySupplier(keyStoreFile, trustedKeystoreType, keyStorePass, keyPass))
        .buildRequestFactory();
    return restTemplate;
  }

  @RequiredArgsConstructor
  private static class ClientHttpRequestFactorySupplier implements Supplier<ClientHttpRequestFactory> {

    private final String keyStoreFile;
    private final String trustedKeystoreType;
    private final String keyStorePass;
    private final String keyPass;

    @Override
    public ClientHttpRequestFactory get() {
      return buildHttpRequestFactory(keyStoreFile, trustedKeystoreType, keyStorePass, keyPass);
    }

    private ClientHttpRequestFactory buildHttpRequestFactory(String keyStoreFile, String trustedKeystoreType, String keyStorePass, String keyPass) {
      SSLConnectionSocketFactory socketFactory;
      try {
        KeyStore keyStore = KeyStore.getInstance(trustedKeystoreType);

        File keyFile = new File(keyStoreFile);
        FileSystemResource fileSystemResource = new FileSystemResource(keyFile);

        InputStream inputStream = fileSystemResource.getInputStream();
        keyStore.load(inputStream, Objects.requireNonNull(keyStorePass)
            .toCharArray());

        socketFactory = new SSLConnectionSocketFactory(
            new SSLContextBuilder().loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .loadKeyMaterial(keyStore, keyPass.toCharArray())
                .build(),
            NoopHostnameVerifier.INSTANCE);
      } catch (Exception e) {
        log.debug("SSL keystore exception", e);
        throw new RuntimeException(e);
      }
      CloseableHttpClient httpClient = HttpClients.custom()
          .setSSLSocketFactory(socketFactory)
          .build();

      return new HttpComponentsClientHttpRequestFactory(httpClient);
    }
  }
}
