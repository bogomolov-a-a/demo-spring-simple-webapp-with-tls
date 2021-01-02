package org.artembogomolova.demo.webapp.dao.repo;

import org.artembogomolova.demo.webapp.domain.business.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends PagingAndSortingRepository<Order, Long> {

}
