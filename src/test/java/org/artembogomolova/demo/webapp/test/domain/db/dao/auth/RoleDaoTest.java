package org.artembogomolova.demo.webapp.test.domain.db.dao.auth;

import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Dao test: Role")
class RoleDaoTest extends AbstractDaoTest<Role> {

  RoleDaoTest() {
    super(Role.class, Role::new);
  }

  @Override
  protected List<Role> updateEntities(List<Role> savedCollection) {
    return null;
  }

  @Override
  protected CrudRepository<Role, Long> getCrudRepository() {
    return null;
  }

  @Override
  protected List<Role> generateEntities() {
    return null;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected Role doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint, Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected Role doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return null;
  }
}
