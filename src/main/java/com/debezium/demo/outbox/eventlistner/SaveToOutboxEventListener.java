package com.debezium.demo.outbox.eventlistner;

import com.debezium.demo.outbox.service.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaveToOutboxEventListener implements ApplicationListener<SaveToOutboxEvent> {

    private final OutboxService outboxService;

    @Override
    public void onApplicationEvent(SaveToOutboxEvent event) {
        outboxService.save(event.getSource(), event.getAggregateType(), event.getAggregateId(), event.getTopicName());
    }
}
