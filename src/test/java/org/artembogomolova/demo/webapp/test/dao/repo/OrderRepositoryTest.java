package org.artembogomolova.demo.webapp.test.dao.repo;

import java.util.ArrayList;
import java.util.List;
import org.artembogomolova.demo.webapp.dao.repo.IOrderRepository;
import org.artembogomolova.demo.webapp.domain.business.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class OrderRepositoryTest extends AbstractDaoTest<Order> {

  @Autowired
  private IOrderRepository orderRepository;

  @Override
  protected List<Order> updateEntities(List<Order> savedCollection) {
    return savedCollection;
  }

  @Override
  protected CrudRepository<Order, Long> getCrudRepository() {
    return orderRepository;
  }

  @Override
  protected List<Order> generateEntities() {
    List<Order> result = new ArrayList<>();
    /*Order createdOrder = RepositoryTestUtil.buildCreatedOrder();
    createdOrder.setOrderAddressPlain("address1");
    result.add(createdOrder);
    Order payedOrder = RepositoryTestUtil.buildPayedOrder();
    payedOrder.setOrderAddressPlain("address2");
    result.add(payedOrder);*/
    Order deliveredOrder = RepositoryTestUtil.buildDeliveredOrder();
    deliveredOrder.setOrderAddressPlain("address3");
    result.add(deliveredOrder);
    return result;
  }
}
