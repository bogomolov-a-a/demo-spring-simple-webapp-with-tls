package org.artembogomolova.demo.webapp.test.domain.entity.auth;

import java.util.function.Function;
import org.artembogomolova.demo.webapp.domain.auth.Authority;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Authority")
class AuthorityEntityTest extends AbstractAccessorEntityTest<Authority> {

  AuthorityEntityTest() {
    super(Authority.class);
  }

  @Override
  protected Authority buildStandardEntity() {
    return null;
  }

  @Override
  protected Authority buildDuplicateEntity(Authority standardEntity) {
    return null;
  }

  @Override
  protected Authority buildAnotherEntityForTest() {
    return null;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Authority standardEntity, String constraintName, String columnName) {

  }

  @Override
  protected Function<Authority, ? extends Authority> getFakeDescendantClassConstructor() {
    return FakeAuthority::new;
  }

  private static class FakeAuthority extends Authority {

    FakeAuthority(Authority authority) {
      super(authority);
    }
  }
}
