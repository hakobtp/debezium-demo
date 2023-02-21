package com.debezium.demo.user.mapper;

import com.debezium.demo.common.mapper.FullMapper;
import com.debezium.demo.user.dto.UserDto;
import com.debezium.demo.user.persistence.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper implements FullMapper<User, UserDto> {

    @Override
    public UserDto toDto(User user) {
        return new UserDto()
                .setId(user.getId())
                .setAge(user.getAge())
                .setEmail(user.getEmail())
                .setStatus(user.getStatus())
                .setVersion(user.getVersion())
                .setPassword(user.getPassword())
                .setLastName(user.getLastName())
                .setFirstName(user.getFirstName());
    }

    @Override
    public User toEntity(UserDto dto) {
        return new User()
                .setId(dto.getId())
                .setAge(dto.getAge())
                .setEmail(dto.getEmail())
                .setLastName(dto.getLastName())
                .setPassword(dto.getPassword())
                .setFirstName(dto.getFirstName());
    }

    @Override
    public List<UserDto> toDtoList(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<User> toEntityList(List<UserDto> userDtoList) {
        return userDtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toUnmodifiableList());
    }
}
