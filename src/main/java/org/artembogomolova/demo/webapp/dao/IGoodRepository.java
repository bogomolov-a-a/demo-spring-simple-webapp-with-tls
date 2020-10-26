package org.artembogomolova.demo.webapp.dao;

import org.artembogomolova.demo.webapp.model.Good;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IGoodRepository extends PagingAndSortingRepository<Good,Long> {

}
