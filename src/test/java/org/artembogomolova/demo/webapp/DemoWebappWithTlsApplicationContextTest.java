package org.artembogomolova.demo.webapp;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.util.RestTemplateUtil;
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
public class DemoWebappWithTlsApplicationContextTest {

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
    log.info("application started at port {}",serverPort);
  }
  @Test
  void tlsValidate() throws IOException {
    TestRestTemplate restTemplate=restTemplateUtil.getSecureRestTemplate(trustedKeystoreFile,trustedKeystoreType,trustedKeystorePassword,trustedKeyPassword);
    ResponseEntity<String> data = restTemplate.getForEntity("https://localhost:" + serverPort, String.class);
    String result = data.getBody();
    if(!data.getStatusCode().equals(HttpStatus.OK)) {
      log.error(result);
      throw new RuntimeException(result);
    }

  }

}
