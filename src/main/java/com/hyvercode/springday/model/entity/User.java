package com.hyvercode.springday.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at=NOW() where user_id=?")
@Where(clause = "deleted_time is NULL")
public class User {

  @Id
  @Column(name = "user_id", length = 36, nullable = false, unique = true)
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "uuid")
  private String userId;

  @Column(name = "username", length = 30, nullable = false)
  private String username;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "name", length = 60, nullable = false)
  private String name;

  @Column(name = "email", length = 30, nullable = false)
  private String email;

  @Column(name = "phone_number", length = 15, nullable = false)
  private String phoneNumber;

  @Column(name = "device_id", length = 60, nullable = false)
  private String deviceId;

  @Column(name = "is_active")
  private Boolean isActive;

  @Column(name = "deleted_time")
  private Timestamp deletedTime;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(name = "users_roles",
    joinColumns = {
      @JoinColumn(name = "user_id")
    },
    inverseJoinColumns = {
      @JoinColumn(name = "role_id") })
  private Set<Role> roles;

}
