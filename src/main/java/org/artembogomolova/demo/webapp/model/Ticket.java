package org.artembogomolova.demo.webapp.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="tickets")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Ticket  implements Serializable {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic
  @Column(columnDefinition = "integer not null primary key autoincrement")
  private Long id;
  private Float summ;
  @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE,CascadeType.DETACH})
  @JoinColumn(name="order_id",columnDefinition = "bigint")
  private Order order;
}
