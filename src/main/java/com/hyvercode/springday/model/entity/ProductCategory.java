package com.hyvercode.springday.model.entity;

import com.hyvercode.springday.helpers.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_category")
public class ProductCategory extends BaseEntity {

  @Id
  @Column(name = "product_category_id", length = 36, nullable = false, unique = true)
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
  private String productCategoryId;

  @Column(name = "product_category_name", length = 60, nullable = false)
  private String productCategoryName;

  @Column(name = "description", length = 100)
  private String description;


  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "deleted_time")
  private Timestamp deletedTime;
}
