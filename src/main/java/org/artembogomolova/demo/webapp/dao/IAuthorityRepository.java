package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.auth.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthorityRepository extends CrudRepository<Authority, Long> {

}
