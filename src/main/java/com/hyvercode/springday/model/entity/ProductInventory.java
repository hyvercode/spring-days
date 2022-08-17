package com.hyvercode.springday.model.entity;

import com.hyvercode.springday.helpers.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_inventory")
@SQLDelete(sql = "UPDATE product SET deleted_time=NOW() where product_inventory_id=?")
@Where(clause = "deleted_time is NULL")
public class ProductInventory extends BaseEntity {

  @Id
  @Column(name = "product_inventory_id", length = 36, nullable = false, unique = true)
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
  private String productInventoryId;

  @Column(name = "quantity")
  private BigInteger quantity;

  @Column(name = "deleted_time")
  private Timestamp deletedTime;
}
