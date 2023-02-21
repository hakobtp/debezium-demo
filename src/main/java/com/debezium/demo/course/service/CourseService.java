package com.debezium.demo.course.service;

import com.debezium.demo.common.persistence.entity.Status;
import com.debezium.demo.course.persistence.entity.Course;
import com.debezium.demo.course.persistence.repository.CourseRepository;
import com.debezium.demo.user.persistence.entity.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.function.BiConsumer;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Optional<Course> read(Long id) {
        return courseRepository.findActiveById(id);
    }

    public Page<Course> findAllActive(Pageable pageable) {
        return courseRepository.findAllActive(pageable);
    }

    public Course create(Course course) {
        Assert.isNull(course.getId(), "course id should be null");
        return courseRepository.save(course);
    }

    public Course update(Course course) {
        Assert.notNull(course.getId(), "course not exist");
        if (courseRepository.existsByStatusAndId(Status.ACTIVE, course.getId())) {
            throw new IllegalArgumentException("course not exist");
        }
        return courseRepository.save(course);
    }

    public Course addAuthor(Long courseId, User user) {
        return addUser(courseId, user, Course::addAuthor);
    }

    public Course addMember(Long courseId, User user) {
        return addUser(courseId, user, Course::addMembers);
    }

    public Course delete(Long id) {
        return courseRepository.findActiveById(id)
                .map(Course::inActive)
                .map(courseRepository::save)
                .orElse(null);
    }

    private Course addUser(Long courseId, User user, BiConsumer<Course, User> consumer) {
        var course = courseRepository.findActiveById(courseId)
                .orElseThrow(EntityNotFoundException::new);

        consumer.accept(course, user);
        return courseRepository.save(course);
    }
}
