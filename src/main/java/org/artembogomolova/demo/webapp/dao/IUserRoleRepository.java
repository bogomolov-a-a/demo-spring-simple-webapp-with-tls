package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.auth.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRoleRepository extends CrudRepository<Role, Long> {

}
