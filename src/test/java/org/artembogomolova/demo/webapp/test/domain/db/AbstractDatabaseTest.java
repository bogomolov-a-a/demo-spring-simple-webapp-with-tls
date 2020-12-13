package org.artembogomolova.demo.webapp.test.domain.db;

import lombok.extern.slf4j.Slf4j;
import org.artembogomolova.demo.webapp.test.AbstractClassTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest()
@ActiveProfiles({"test"})
@Slf4j
@AutoConfigureTestDatabase(replace = Replace.NONE, connection = EmbeddedDatabaseConnection.NONE)
public abstract class AbstractDatabaseTest<T> extends AbstractClassTest<T> {

  protected AbstractDatabaseTest(Class<T> testingClass,
      String classNameSuffix,
      String displayNamePrefix) {
    super(testingClass, classNameSuffix, displayNamePrefix);
  }

}
