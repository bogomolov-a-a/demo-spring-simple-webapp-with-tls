package org.artembogomolova.demo.webapp.domain.business;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;

@Entity
@Table(name = "producers")
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Producer extends IdentifiedEntity {

  private String name;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "producer_address_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  private PhysicalAddress address;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "producer")
  private List<Good> goods = new ArrayList<>();

  public Producer(Producer producer) {
    this.setName(producer.getName());
    this.setAddress(producer.getAddress());
    this.getGoods().addAll(producer.getGoods().stream().map(this::createNewGood).collect(Collectors.toList()));
  }

  private Good createNewGood(Good good) {
    Good result = new Good(good);
    result.setProducer(this);
    return result;
  }
}
