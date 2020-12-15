package org.artembogomolova.demo.webapp.test.domain.db.dao.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.dao.repo.auth.IAuthorityRepository;
import org.artembogomolova.demo.webapp.domain.auth.Authority;
import org.artembogomolova.demo.webapp.domain.auth.Authority_;
import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Dao test: Authority")
class AuthorityDaoTest extends AbstractDaoTest<Authority> {

  private static final String NAME_VALUE = "test:authority";
  private static final String DESCRIPTION_VALUE = "test description";
  private static final String NAME_ANOTHER_VALUE = "test:authority_another";
  @Autowired
  private IAuthorityRepository authorityRepository;

  AuthorityDaoTest() {
    super(Authority.class, Authority::new);
  }

  @Override
  protected List<Authority> updateEntities(List<Authority> savedCollection) {
    savedCollection.get(0).setDescription(DESCRIPTION_VALUE + "1");
    return savedCollection;
  }

  @Override
  protected CrudRepository<Authority, Long> getCrudRepository() {
    return authorityRepository;
  }

  @Override
  protected List<Authority> generateEntities() {
    Authority result = buildAuthority();
    return List.of(result);
  }

  private Authority buildAuthority() {
    Authority result = new Authority();
    result.setName(NAME_VALUE);
    result.setDescription(DESCRIPTION_VALUE);
    return result;
  }

  @Override
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    Map<String, Object> result = new HashMap<>();
    result.put(Authority_.ROLES, new ArrayList<>());
    return result;
  }

  @Override
  protected Authority doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint, Map<String, Object> commonValues) {
    return buildAuthority(commonValues);
  }

  @Override
  protected Authority doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return buildAuthority(commonValues);
  }

  private Authority buildAuthority(Map<String, Object> commonValues) {
    Authority result = buildAuthority();
    //result.setRoles((List<Role>) commonValues.get(Authority_.ROLES));
    return result;
  }

  @Override
  protected Authority buildEntityWithoutViolationEntity() {
    return buildAuthority();
  }
}
