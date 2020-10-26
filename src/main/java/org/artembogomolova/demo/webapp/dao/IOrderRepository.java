package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.Order;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IOrderRepository extends PagingAndSortingRepository<Order,Long> {

}
