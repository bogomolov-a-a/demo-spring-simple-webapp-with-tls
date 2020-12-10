package org.artembogomolova.demo.webapp.test.context.web;

import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.test.context.AbstractContextLoadTest;
import org.artembogomolova.demo.webapp.test.util.RestTemplateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Slf4j
@DisplayName("Basically test for data exchange through security channel(HTTPS)")
class HttpsProtocolExchangeOnTest extends AbstractContextLoadTest {

  @Value("${test.ssl.trusted-keystore-file}")
  private String trustedKeystoreFile;
  @Value("${test.ssl.trusted-store-type}")
  private String trustedKeystoreType;
  @Value("${test.ssl.trusted-store-password}")
  private String trustedKeystorePassword;
  @Value("${test.ssl.trusted-key-password}")
  private String trustedKeyPassword;
  @Autowired
  private RestTemplateUtil restTemplateUtil;

  HttpsProtocolExchangeOnTest(@LocalServerPort int serverPort) {
    super(serverPort);
  }

  @Test
  @DisplayName("Validated tls connection established, try to get index page by https with generated server certificate.")
  void validateTLSConnectionCreatedAndIndexPageGet() {
    checkServerPortAssigned();
    TestRestTemplate restTemplate = restTemplateUtil.getSecureRestTemplate(trustedKeystoreFile,
        trustedKeystoreType,
        trustedKeystorePassword,
        trustedKeyPassword);
    log.info("try to get index page in secured connection.");
    ResponseEntity<String> data = restTemplate.getForEntity("https://localhost:" + getServerPort() + "/", String.class);
    String result = data.getBody();
    Assertions.assertEquals(HttpStatus.OK, data.getStatusCode(), result);
    log.info("index page in secured connection successful get!");
  }
}
