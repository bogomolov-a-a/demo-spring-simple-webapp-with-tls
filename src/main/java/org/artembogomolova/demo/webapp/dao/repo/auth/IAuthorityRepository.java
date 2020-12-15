package org.artembogomolova.demo.webapp.dao.repo.auth;

import org.artembogomolova.demo.webapp.domain.auth.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorityRepository extends CrudRepository<Authority, Long> {

  Authority findByName(String name);
}
