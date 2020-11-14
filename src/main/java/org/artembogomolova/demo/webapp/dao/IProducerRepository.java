package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.business.Producer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IProducerRepository extends PagingAndSortingRepository<Producer, Long> {

}
