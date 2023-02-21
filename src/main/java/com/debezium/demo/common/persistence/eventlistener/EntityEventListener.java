package com.debezium.demo.common.persistence.eventlistener;

import com.debezium.demo.common.mapper.Mapper;
import com.debezium.demo.outbox.eventlistner.SaveToOutboxEvent;

public abstract class EntityEventListener<E, D> {

    private final Mapper<E, D> mapper;

    protected EntityEventListener(Mapper<E, D> mapper) {
        this.mapper = mapper;
    }

    protected SaveToOutboxEvent createEvent(E e, Long id, String topicName, SaveToOutboxEvent.EventType eventType) {
        return new SaveToOutboxEvent(mapper.toDto(e), id.toString(), eventType, topicName);
    }
}
