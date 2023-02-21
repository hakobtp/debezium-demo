package com.debezium.demo.user.eventlistener;

import com.debezium.demo.common.mapper.Mapper;
import com.debezium.demo.common.persistence.eventlistener.EntityEventListener;
import com.debezium.demo.outbox.eventlistner.SaveToOutboxEvent;
import com.debezium.demo.user.dto.UserDto;
import com.debezium.demo.user.persistence.entity.User;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.context.ApplicationEventPublisher;

public class UserEntityEventListener extends EntityEventListener<User, UserDto> {

    private static final String TOPIC = "user-incremental";
    private final ApplicationEventPublisher eventPublisher;

    protected UserEntityEventListener(Mapper<User, UserDto> mapper, ApplicationEventPublisher eventPublisher) {
        super(mapper);
        this.eventPublisher = eventPublisher;
    }

    @PostPersist
    private void postPersist(User user) {
        var event = createEvent(user, user.getId(), TOPIC, SaveToOutboxEvent.EventType.CREATE);
        eventPublisher.publishEvent(event);
    }

    @PostUpdate
    private void postUpdate(User user) {
        var eventType = user.isActive() ? SaveToOutboxEvent.EventType.UPDATE : SaveToOutboxEvent.EventType.INACTIVE;
        var event = createEvent(user, user.getId(), TOPIC, eventType);
        eventPublisher.publishEvent(event);
    }

    @PostRemove
    private void postRemove(User user) {
        var event = createEvent(user, user.getId(), TOPIC, SaveToOutboxEvent.EventType.DELETE);
        eventPublisher.publishEvent(event);
    }
}
