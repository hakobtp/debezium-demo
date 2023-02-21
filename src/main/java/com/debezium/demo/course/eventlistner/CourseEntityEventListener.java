package com.debezium.demo.course.eventlistner;

import com.debezium.demo.common.mapper.Mapper;
import com.debezium.demo.common.persistence.eventlistener.EntityEventListener;
import com.debezium.demo.course.dto.CourseDto;
import com.debezium.demo.course.persistence.entity.Course;
import com.debezium.demo.outbox.eventlistner.SaveToOutboxEvent;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.context.ApplicationEventPublisher;

public class CourseEntityEventListener extends EntityEventListener<Course, CourseDto> {

    private static final String TOPIC = "course_incremental";
    private final ApplicationEventPublisher eventPublisher;

    protected CourseEntityEventListener(Mapper<Course, CourseDto> mapper, ApplicationEventPublisher eventPublisher) {
        super(mapper);
        this.eventPublisher = eventPublisher;
    }

    @PostPersist
    private void postPersist(Course course) {
        var event = createEvent(course, course.getId(), TOPIC, SaveToOutboxEvent.EventType.CREATE);
        eventPublisher.publishEvent(event);
    }

    @PostUpdate
    private void postUpdate(Course course) {
        var eventType = course.isActive() ? SaveToOutboxEvent.EventType.UPDATE : SaveToOutboxEvent.EventType.INACTIVE;
        var event = createEvent(course, course.getId(), TOPIC, eventType);
        eventPublisher.publishEvent(event);
    }

    @PostRemove
    private void postRemove(Course course) {
        var event = createEvent(course, course.getId(), TOPIC, SaveToOutboxEvent.EventType.DELETE);
        eventPublisher.publishEvent(event);
    }
}
