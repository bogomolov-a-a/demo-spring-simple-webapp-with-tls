package org.artembogomolova.demo.webapp.dao.repo.business;

import org.artembogomolova.demo.webapp.domain.business.OrderPosition;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderPositionRepository extends PagingAndSortingRepository<OrderPosition, Long> {

}
