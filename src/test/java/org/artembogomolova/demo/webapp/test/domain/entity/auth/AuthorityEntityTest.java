package org.artembogomolova.demo.webapp.test.domain.entity.auth;

import lombok.EqualsAndHashCode;
import org.artembogomolova.demo.webapp.domain.auth.Authority;
import org.artembogomolova.demo.webapp.domain.auth.Authority_;
import org.artembogomolova.demo.webapp.test.domain.entity.AbstractAccessorEntityTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

@DisplayName("Entity test: Authority")
class AuthorityEntityTest extends AbstractAccessorEntityTest<Authority> {

  private static final String NAME_VALUE = "test:authority";
  private static final String DESCRIPTION_VALUE = "test description";
  private static final String NAME_ANOTHER_VALUE = "test:authority_another";

  AuthorityEntityTest() {
    super(Authority.class,
        Authority::new,
        MockAuthority::new);
  }

  @Override
  protected Authority buildStandardEntity() {
    Authority result = new Authority();
    result.setName(NAME_VALUE);
    result.setDescription(DESCRIPTION_VALUE);
    return result;
  }

  @Override
  protected void containFieldCorrectValuesTest(Authority standardEntity) {
    Assertions.assertEquals(NAME_VALUE, standardEntity.getName());
    Assertions.assertEquals(NAME_VALUE, standardEntity.getAuthority());
    Assertions.assertTrue(standardEntity.getRoles().isEmpty());
    Assertions.assertTrue(standardEntity.getUsers().isEmpty());
    Assertions.assertEquals(DESCRIPTION_VALUE, standardEntity.getDescription());
  }

  @Override
  protected Authority buildAnotherEntityForTest() {

    Authority result = new Authority();
    result.setName(NAME_ANOTHER_VALUE);
    return result;
  }

  @Override
  protected void withoutPartOfUniqueConstraintEqualTest(Authority standardEntity, String constraintName, String columnName) {
    if (Authority_.NAME.equals(columnName)) {
      withoutColumnEqualTest(standardEntity, Authority::getName, Authority::setName);
    }

  }

  @EqualsAndHashCode(callSuper = false)
  private static class MockAuthority extends Authority {

    MockAuthority(Authority authority) {
      super();
      this.setName(NAME_ANOTHER_VALUE);
    }
  }
}
