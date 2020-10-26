package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.OrderPosition;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IOrderPositionRepository extends PagingAndSortingRepository<OrderPosition,Long> {

}
