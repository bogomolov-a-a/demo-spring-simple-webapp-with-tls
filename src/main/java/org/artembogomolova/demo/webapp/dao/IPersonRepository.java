package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface IPersonRepository extends CrudRepository<Person,Long> {

}
