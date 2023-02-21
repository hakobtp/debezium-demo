package com.debezium.demo.user.persistence.entity;

import com.debezium.demo.common.persistence.entity.AbstractModificationInfoBaseEntity;
import com.debezium.demo.user.eventlistener.UserEntityEventListener;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Table(name = "users")
@Accessors(chain = true)
@EntityListeners(UserEntityEventListener.class)
public class User extends AbstractModificationInfoBaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Integer age;

    @Override
    public User inActive() {
        return (User) super.inActive();
    }
}
