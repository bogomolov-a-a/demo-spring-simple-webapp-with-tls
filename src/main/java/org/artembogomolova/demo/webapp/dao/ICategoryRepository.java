package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.business.Category;
import org.springframework.data.repository.CrudRepository;

public interface ICategoryRepository extends CrudRepository<Category, Long> {

}
