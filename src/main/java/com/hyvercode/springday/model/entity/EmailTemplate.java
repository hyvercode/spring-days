package com.hyvercode.springday.model.entity;

import com.hyvercode.springday.helpers.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

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

  @Column(name = "event_code")
  private String eventCode;

  @Column(name = "en_title")
  private String enTitle;

  @Column(name = "id_title")
  private String idTitle;

  @Lob
  @Type(type = "org.hibernate.type.TextType")
  @Column(name = "en_content",columnDefinition = "text")
  private String enContent;

  @Lob
  @Type(type = "org.hibernate.type.TextType")
  @Column(name = "id_content",columnDefinition = "text")
  private String idContent;

  @Column(name = "image_content")
  private String imageContent;

  @Column(name = "is_active")
  private Boolean isActive;
}
