package org.artembogomolova.demo.webapp.dao.repo.business;

import org.artembogomolova.demo.webapp.domain.business.ActionGood;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IActionGoodRepository extends PagingAndSortingRepository<ActionGood, Long> {

}
