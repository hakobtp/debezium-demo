package com.debezium.demo.outbox.persistence.repository;

import com.debezium.demo.outbox.persistence.entity.Outbox;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<Outbox, Long> {
}
