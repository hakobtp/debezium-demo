package com.debezium.demo.course.dto;

import com.debezium.demo.common.persistence.entity.Status;
import com.debezium.demo.user.dto.UserDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class CourseDto {

    private Long id;

    @NotBlank
    private String name;

    @Size(min = 10, max = 256)
    private String description;

    private Status status;

    private Integer version;

    private List<UserDto> authors = new ArrayList<>();

    private List<UserDto> members = new ArrayList<>();
}
