package org.artembogomolova.demo.webapp.test.domain.db.dao.auth;

import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.domain.auth.User;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Dao test: User")
class UserDaoTest extends AbstractDaoTest<User> {

   UserDaoTest() {
    super(User.class);
  }

  @Override
  protected List<User> updateEntities(List<User> savedCollection) {
    return null;
  }

  @Override
  protected CrudRepository<User, Long> getCrudRepository() {
    return null;
  }

  @Override
  protected List<User> generateEntities() {
    return null;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected User doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint, Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected User doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return null;
  }
}
