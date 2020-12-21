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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.business.IProducerRepository;
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.ConstraintPatterns;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.domain.core.PhysicalAddress;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;

@Entity
@Table(name = "producers")
@NoArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@UniqueMultiColumn(repository = IProducerRepository.class,
    constraints = {@UniqueMultiColumnConstraint(name = IdentifiedEntity.BASIC_CONSTRAINT_NAME,
        columnNames = {Producer_.UNIQUE_CODE})})
public class Producer extends IdentifiedEntity {

  @NotBlank
  @Setter
  @ToString.Include
  private String name;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "producer_address_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  @Setter
  @NotNull
  @ToString.Include
  private PhysicalAddress address;
  @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH}, orphanRemoval = true, mappedBy = "producer")
  private List<Good> goods = new ArrayList<>();
  @Setter
  @NotBlank
  @ToString.Include
  @Pattern(regexp = ConstraintPatterns.PHONE_PATTERN)
  private String contactPhone;
  @Setter
  @NotBlank
  @ToString.Include
  @EqualsAndHashCode.Include
  private String uniqueCode;

  public Producer(Producer producer) {
    this.setName(producer.getName());
    this.setContactPhone(producer.getContactPhone());
    this.setAddress(producer.getAddress());
    this.setUniqueCode(producer.getUniqueCode());
    this.getGoods().addAll(producer.getGoods().stream().map(this::createNewGood).collect(Collectors.toList()));
  }

  private Good createNewGood(Good good) {
    Good result = new Good(good);
    result.setProducer(this);
    return result;
  }
}
