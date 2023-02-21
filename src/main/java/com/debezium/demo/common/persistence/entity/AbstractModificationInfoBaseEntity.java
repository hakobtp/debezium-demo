package com.debezium.demo.common.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;

@Getter
@Setter
@MappedSuperclass
@Accessors(chain = true)
@EntityListeners({AuditingEntityListener.class})
public abstract class AbstractModificationInfoBaseEntity {

    @CreatedDate
    @Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private String createdBy;

    @LastModifiedDate
    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime modifiedAt;

    @LastModifiedBy
    @Column(nullable = false)
    private String modifiedBy;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Version
    private Integer version;

    public AbstractModificationInfoBaseEntity inActive() {
        status = Status.INACTIVE;
        return this;
    }

    public boolean isActive() {
        return status == Status.ACTIVE;
    }

}

