package org.artembogomolova.demo.webapp.dao.repo.auth;

import org.artembogomolova.demo.webapp.domain.auth.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends PagingAndSortingRepository<User, Long> {

  User findByLogin(String login);
}
