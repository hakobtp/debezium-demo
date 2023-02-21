package com.debezium.demo.course.mapper;

import com.debezium.demo.common.mapper.Mapper;
import com.debezium.demo.course.dto.CourseDto;
import com.debezium.demo.course.persistence.entity.Course;
import com.debezium.demo.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper implements Mapper<Course, CourseDto> {

    private final UserMapper userMapper;

    @Override
    public CourseDto toDto(Course course) {
        return new CourseDto()
                .setId(course.getId())
                .setName(course.getName())
                .setStatus(course.getStatus())
                .setVersion(course.getVersion())
                .setDescription(course.getDescription())
                .setAuthors(userMapper.toDtoList(course.getAuthors()))
                .setMembers(userMapper.toDtoList(course.getMembers()));
    }

    @Override
    public Course toEntity(CourseDto dto) {
        return new Course()
                .setId(dto.getId())
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setAuthors(userMapper.toEntityList(dto.getAuthors()))
                .setMembers(userMapper.toEntityList(dto.getMembers()));
    }
}
