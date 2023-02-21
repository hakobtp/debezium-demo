package com.debezium.demo.user.facade;

import com.debezium.demo.user.dto.UserDto;
import com.debezium.demo.user.mapper.UserMapper;
import com.debezium.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Component
@Transactional
@RequiredArgsConstructor
public class UserFacade {

    private final UserMapper userMapper;
    private final UserService userService;

    @Transactional(readOnly = true)
    public UserDto read(Long id) {
        var user = userService.read(id);
        return user.map(userMapper::toDto).orElse(null);
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAll(Pageable pageable) {
        return userService.findAllActive(pageable).map(userMapper::toDto);
    }

    public UserDto create(UserDto userDto) {
        userDto.setId(null);
        var user = userService.create(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    public UserDto update(UserDto userDto) {
        Assert.notNull(userDto.getId(), "invalid data");
        var user = userService.update(userMapper.toEntity(userDto));
        return userMapper.toDto(user);
    }

    public UserDto updateEmail(Long id, String email) {
        return userMapper.toDto(userService.updateEmail(id, email));
    }

    public UserDto delete(Long id) {
        return userMapper.toDto(userService.delete(id));
    }
}
