package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.business.OrderPosition;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderPositionRepository extends PagingAndSortingRepository<OrderPosition, Long> {

}
