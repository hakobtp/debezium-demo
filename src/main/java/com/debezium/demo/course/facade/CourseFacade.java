package com.debezium.demo.course.facade;

import com.debezium.demo.course.dto.CourseDto;
import com.debezium.demo.course.mapper.CourseMapper;
import com.debezium.demo.course.service.CourseService;
import com.debezium.demo.user.service.UserService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
@RequiredArgsConstructor
public class CourseFacade {

    private final UserService userService;
    private final CourseMapper courseMapper;
    private final CourseService courseService;

    @Transactional(readOnly = true)
    public CourseDto read(Long id) {
        return courseService.read(id).map(courseMapper::toDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<CourseDto> findAllActive(Pageable pageable) {
        return courseService.findAllActive(pageable).map(courseMapper::toDto);
    }

    public CourseDto create(CourseDto courseDto) {
        var createdCourse = courseService.create(courseMapper.toEntity(courseDto));
        return courseMapper.toDto(createdCourse);
    }

    public CourseDto update(CourseDto courseDto) {
        var updatedCourse = courseService.update(courseMapper.toEntity(courseDto));
        return courseMapper.toDto(updatedCourse);
    }

    public CourseDto addAuthor(Long courseId, Long userId) {
        var author = userService.read(userId).orElseThrow(EntityExistsException::new);
        var course = courseService.addAuthor(courseId, author);
        return courseMapper.toDto(course);
    }

    public CourseDto addMember(Long courseId, Long userId) {
        var member = userService.read(userId).orElseThrow(EntityExistsException::new);
        var course = courseService.addMember(courseId, member);
        return courseMapper.toDto(course);
    }

    public void delete(Long id) {
        courseService.delete(id);
    }
}
