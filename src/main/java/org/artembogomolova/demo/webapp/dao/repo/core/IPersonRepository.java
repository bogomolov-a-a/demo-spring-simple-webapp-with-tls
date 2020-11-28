package org.artembogomolova.demo.webapp.dao.repo.core;

import org.artembogomolova.demo.webapp.domain.core.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPersonRepository extends CrudRepository<Person, Long> {

}
