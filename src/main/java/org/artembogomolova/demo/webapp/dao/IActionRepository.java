package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.Action;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IActionRepository extends PagingAndSortingRepository<Action,Long> {

}
