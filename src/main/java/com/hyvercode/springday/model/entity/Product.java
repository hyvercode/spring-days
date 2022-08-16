package com.hyvercode.springday.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
@SQLDelete(sql = "UPDATE product SET deleted_at=NOW() where product_id=?")
@Where(clause = "deleted_time is NULL")
public class Product {

    @Id
    @Column(name = "product_id",length = 36,nullable = false,unique = true)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String productId;

    @Column(name = "product_name",length = 60,nullable = false)
    private String productName;

    @Column(name = "deleted_time")
    private Timestamp deletedTime;
}
