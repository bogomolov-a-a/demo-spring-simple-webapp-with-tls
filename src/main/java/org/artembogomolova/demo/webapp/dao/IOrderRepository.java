package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.business.Order;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends PagingAndSortingRepository<Order, Long> {

}
