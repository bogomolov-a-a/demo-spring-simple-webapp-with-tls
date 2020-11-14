package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.business.Action;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActionRepository extends PagingAndSortingRepository<Action, Long> {

}
