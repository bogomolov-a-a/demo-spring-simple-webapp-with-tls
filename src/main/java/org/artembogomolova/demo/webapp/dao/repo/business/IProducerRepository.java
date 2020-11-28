package org.artembogomolova.demo.webapp.dao.repo.business;

import org.artembogomolova.demo.webapp.domain.business.Producer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProducerRepository extends PagingAndSortingRepository<Producer, Long> {

}
