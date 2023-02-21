package com.debezium.demo.outbox.service;

import com.debezium.demo.outbox.persistence.entity.Outbox;
import com.debezium.demo.outbox.persistence.repository.OutboxRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class OutboxService {

    private final ObjectMapper objectMapper;
    private final OutboxRepository outboxRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public <T> Outbox save(T payload, String aggregateType, String aggregateId, String topicName) {
        validate(payload, aggregateType, aggregateId);
        var json = asJson(payload);
        var outbox = new Outbox()
                .setPayload(json)
                .setType(createType(payload))
                .setAggregateType(aggregateType)
                .setAggregateId(aggregateId)
                .setTopicName(topicName);
        return outboxRepository.save(outbox);
    }

    private <T> String asJson(T payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> String createType(T payload) {
        var split = payload.getClass().getTypeName().split("\\.");
        if (split.length > 0) {
            return split[split.length - 1];
        }
        throw new RuntimeException("invalid type: " + payload.getClass().getTypeName());
    }

    private void validate(Object payload, String aggregateType, String aggregateId) {
        Assert.notNull(payload, "payload can't be null");
        Assert.notNull(aggregateId, "aggregateId can't be null");
        Assert.notNull(aggregateType, "aggregateType can't be null");
    }
}
