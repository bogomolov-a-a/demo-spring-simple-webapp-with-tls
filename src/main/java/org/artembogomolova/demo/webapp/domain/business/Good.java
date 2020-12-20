package org.artembogomolova.demo.webapp.domain.business;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.artembogomolova.demo.webapp.dao.repo.business.IGoodRepository;
import org.artembogomolova.demo.webapp.dao.util.SQLite3Dialect;
import org.artembogomolova.demo.webapp.domain.IdentifiedEntity;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn;
import org.artembogomolova.demo.webapp.validation.UniqueMultiColumn.UniqueMultiColumnConstraint;

@Entity
@Table(name = "goods")
@NoArgsConstructor
@Getter
@ToString(exclude = {Good_.PRODUCER, Good_.CATEGORY})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@UniqueMultiColumn(repository = IGoodRepository.class, constraints = @UniqueMultiColumnConstraint(name = IdentifiedEntity.BASIC_CONSTRAINT_NAME,
    columnNames = {Good_.NAME, Good_.PRODUCER, Good_.CATEGORY}))
public class Good extends IdentifiedEntity {

  @EqualsAndHashCode.Include
  @Setter
  private String name;
  @Setter
  private String description;
  @Setter
  private String imgFilePath;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "producer_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  @Setter
  @EqualsAndHashCode.Include
  private Producer producer;
  @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.DETACH})
  @JoinColumn(name = "category_id", columnDefinition = SQLite3Dialect.FOREIGN_KEY_COLUMN_DEFINITION)
  @Setter
  @EqualsAndHashCode.Include
  private Category category;

  public Good(Good good) {
    this.setName(good.getName());
    this.setDescription(good.getDescription());
    this.setImgFilePath(good.getImgFilePath());
    this.setProducer(good.getProducer() == null ? null : new Producer(good.getProducer()));
    this.setCategory(good.getCategory() == null ? null : new Category(good.getCategory()));
  }
}
