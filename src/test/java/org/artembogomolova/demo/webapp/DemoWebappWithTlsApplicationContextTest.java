package org.artembogomolova.demo.webapp;

import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.util.RestTemplateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = {DemoWebappWithTlsApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles(value = {"test"})
@Slf4j
class DemoWebappWithTlsApplicationContextTest {

  @LocalServerPort
  private int serverPort;

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

  @Test
  void contextLoads() {
    Assertions.assertNotEquals(0, serverPort, "Port is 0! This is wrong way!");
    log.info("application started at port {}", serverPort);
  }

  @Test
  void tlsValidate() {
    TestRestTemplate restTemplate = restTemplateUtil.getSecureRestTemplate(trustedKeystoreFile,
            trustedKeystoreType,
            trustedKeystorePassword,
            trustedKeyPassword);
    ResponseEntity<String> data = restTemplate.getForEntity("https://localhost:" + serverPort, String.class);
    String result = data.getBody();
    Assertions.assertEquals(HttpStatus.OK, data.getStatusCode(), result);
  }

}
