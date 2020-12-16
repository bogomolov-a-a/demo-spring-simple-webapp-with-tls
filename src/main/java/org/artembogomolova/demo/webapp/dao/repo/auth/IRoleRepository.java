package org.artembogomolova.demo.webapp.dao.repo.auth;

import org.artembogomolova.demo.webapp.domain.auth.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends CrudRepository<Role, Long> {

  Role findByName(String name);
}
