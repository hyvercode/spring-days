package com.hyvercode.springday.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET deleted_at=NOW() where product_id=?")
@Where(clause = "deleted_time is NULL")
public class Product extends BaseEntity{

    @Id
    @Column(name = "product_id",length = 36,nullable = false,unique = true)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String productId;

    @Column(name = "sku",length = 36,nullable = false)
    private String sku;

    @Column(name = "product_name",length = 60,nullable = false)
    private String productName;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "deleted_time")
    private Timestamp deletedTime;
}
