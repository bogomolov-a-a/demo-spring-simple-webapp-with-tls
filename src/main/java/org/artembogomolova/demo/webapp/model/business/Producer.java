package org.artembogomolova.demo.webapp.model.business;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import org.artembogomolova.demo.webapp.model.core.IdentifiedEntity;
import org.artembogomolova.demo.webapp.model.core.PhysicalAddress;

@Entity
@Table(name = "producers")
@Data
public class Producer extends IdentifiedEntity {

  private String name;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "producer_address_id", columnDefinition = "bigint")
  private PhysicalAddress address;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "producer")
  private List<Good> goods = new ArrayList<>();

}
