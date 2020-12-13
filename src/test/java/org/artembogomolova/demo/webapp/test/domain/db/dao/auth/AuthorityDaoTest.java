package org.artembogomolova.demo.webapp.test.domain.db.dao.auth;

import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.domain.auth.Authority;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Dao test: Authority")
class AuthorityDaoTest extends AbstractDaoTest<Authority> {

  AuthorityDaoTest() {
    super(Authority.class, Authority::new);
  }

  @Override
  protected List<Authority> updateEntities(List<Authority> savedCollection) {
    return null;
  }

  @Override
  protected CrudRepository<Authority, Long> getCrudRepository() {
    return null;
  }

  @Override
  protected List<Authority> generateEntities() {
    return null;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected Authority doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint, Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected Authority doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return null;
  }
}
