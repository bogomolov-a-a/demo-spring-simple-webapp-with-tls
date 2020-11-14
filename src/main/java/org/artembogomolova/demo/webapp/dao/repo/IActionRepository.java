package org.artembogomolova.demo.webapp.dao.repo;

import org.artembogomolova.demo.webapp.domain.business.Action;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActionRepository extends PagingAndSortingRepository<Action, Long> {

}
