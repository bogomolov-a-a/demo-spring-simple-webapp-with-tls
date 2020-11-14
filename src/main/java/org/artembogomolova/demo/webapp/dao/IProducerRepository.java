package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.business.Producer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProducerRepository extends PagingAndSortingRepository<Producer, Long> {

}
