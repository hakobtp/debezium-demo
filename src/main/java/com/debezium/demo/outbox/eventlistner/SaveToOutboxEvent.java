package com.debezium.demo.outbox.eventlistner;

import org.springframework.context.ApplicationEvent;

public class SaveToOutboxEvent extends ApplicationEvent {

    public enum EventType {
        CREATE,
        UPDATE,
        DELETE,
        INACTIVE
    }

    private final String topicName;
    private final String aggregateId;
    private final String aggregateType;

    public SaveToOutboxEvent(Object source, String aggregateId, EventType eventType, String topicName) {
        super(source);
        this.topicName = topicName;
        this.aggregateId = aggregateId;
        this.aggregateType = eventType.name();
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public String getTopicName() {
        return topicName;
    }
}
