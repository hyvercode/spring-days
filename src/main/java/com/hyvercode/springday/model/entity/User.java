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
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at=NOW() where user_id=?")
@Where(clause = "deleted_time is NULL")
public class User {

    @Id
    @Column(name = "user_id",length = 36,nullable = false,unique = true)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    private String userId;

    @Column(name = "name",length = 60,nullable = false)
    private String name;

    @Column(name = "email",length = 30,nullable = false)
    private String email;

    @Column(name = "deleted_time")
    private Timestamp deletedTime;
}
