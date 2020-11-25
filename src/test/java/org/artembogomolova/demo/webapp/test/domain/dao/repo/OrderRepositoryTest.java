package org.artembogomolova.demo.webapp.test.domain.dao.repo;

import java.util.ArrayList;
import java.util.List;
import org.artembogomolova.demo.webapp.dao.repo.IOrderRepository;
import org.artembogomolova.demo.webapp.domain.business.Order;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumnConstraint.UniqueMultiColumnConstraintColumns;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

public class OrderRepositoryTest extends AbstractDaoTest<Order> {

  @Autowired
  private IOrderRepository orderRepository;

  protected OrderRepositoryTest() {
    super(Order.class);
  }

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
    Order deliveredOrder = DomainTestUtil.buildDeliveredOrder();
    deliveredOrder.setOrderAddressPlain("address3");
    result.add(deliveredOrder);
    return result;
  }

  @Override
  protected Order doDuplicateDeniedTestEntity(UniqueMultiColumnConstraintColumns columns) {
    return null;
  }
}
