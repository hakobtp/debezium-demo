package com.debezium.demo.user.service;

import com.debezium.demo.common.persistence.entity.Status;
import com.debezium.demo.user.persistence.entity.User;
import com.debezium.demo.user.persistence.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Optional<User> read(Long id) {
        return userRepository.findActiveById(id);
    }

    public Page<User> findAllActive(Pageable pageable) {
        return userRepository.findAllActive(pageable);
    }

    public User create(User user) {
        Assert.isNull(user.getId(), "user id should be null");
        return userRepository.save(user);
    }

    public User update(User user) {
        Assert.notNull(user.getId(), "user not exist");
        if (userRepository.existsByStatusAndId(Status.ACTIVE, user.getId())) {
            throw new IllegalArgumentException("user not exist");
        }
        return userRepository.save(user);
    }

    public User updateEmail(Long id, String email) {
        var user = userRepository.findActiveById(id)
                .orElseThrow(EntityExistsException::new);

        user.setEmail(email);
        userRepository.save(user);
        return user;
    }

    public User delete(Long id) {
        return userRepository.findActiveById(id)
                .map(User::inActive)
                .map(userRepository::save)
                .orElse(null);
    }
}
