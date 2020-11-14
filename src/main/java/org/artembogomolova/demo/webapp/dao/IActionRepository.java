package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.business.Action;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IActionRepository extends PagingAndSortingRepository<Action, Long> {

}
