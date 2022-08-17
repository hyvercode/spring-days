package com.hyvercode.springday.helpers.base;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.sql.Timestamp;

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {
    @Column(name = "created_by", length = 50, nullable = false)
    private String createdBy;

    @Column(name = "created_time", nullable = false, updatable=false)
    @CreationTimestamp
    private Timestamp createdTime;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;

    @Column(name = "updated_time")
    @UpdateTimestamp
    private Timestamp updatedTime;
}
