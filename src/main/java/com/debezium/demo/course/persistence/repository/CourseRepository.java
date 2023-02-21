package com.debezium.demo.course.persistence.repository;

import com.debezium.demo.common.persistence.repository.CommonRepository;
import com.debezium.demo.course.persistence.entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CommonRepository<Course, Long> {
}
