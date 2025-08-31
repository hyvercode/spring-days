package com.hyvercode.springday.model.entity;

import com.hyvercode.springday.helpers.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import jakarta.persistence.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "email_template")
public class EmailTemplate extends BaseEntity {
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
  @Column(name = "email_template_id")
  private String emailTemplateId;

  @Column(name = "email_code")
  private String emailCode;

  @Lob
  @Column(name = "content",columnDefinition = "text")
  private String content;

  @Column(name = "is_active")
  private Boolean isActive;
}
