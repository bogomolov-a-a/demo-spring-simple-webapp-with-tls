package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.business.Good;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGoodRepository extends PagingAndSortingRepository<Good, Long> {

}
