package org.artembogomolova.demo.webapp.test.domain.db.dao.business;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.artembogomolova.demo.webapp.dao.repo.business.IOrderRepository;
import org.artembogomolova.demo.webapp.domain.business.Order;
import org.artembogomolova.demo.webapp.test.domain.DomainTestUtil;
import org.artembogomolova.demo.webapp.test.domain.db.dao.AbstractDaoTest;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

@DisplayName("Order entity repository test")
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
  protected Map<String, Object> buildCommonFieldValues(UniqueMultiColumnConstraint uniqueMultiColumnConstraint) {
    return null;
  }

  @Override
  protected Order doPrepareDeniedTestEntity(UniqueMultiColumnConstraint uniqueMultiColumnConstraint,
      Map<String, Object> commonValues) {
    return null;
  }

  @Override
  protected Order doDuplicateDeniedTestEntity(UniqueMultiColumnConstraint columns, Map<String, Object> commonValues) {
    return null;
  }
}
