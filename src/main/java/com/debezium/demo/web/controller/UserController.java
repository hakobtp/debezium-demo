package com.debezium.demo.web.controller;

import com.debezium.demo.user.dto.UserDto;
import com.debezium.demo.user.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserFacade facade;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> read(@PathVariable Long id) {
        return ResponseEntity.ok(facade.read(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(facade.findAll(pageable));
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody UserDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(facade.create(dto));
    }

    @PutMapping
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto dto) {
        return ResponseEntity.ok(facade.update(dto));
    }

    @PatchMapping("/{id}/update-email")
    public ResponseEntity<Void> updateEmail(@PathVariable Long id, @RequestBody UpdateUserEmailCommand command) {
        facade.updateEmail(id, command.getEmail());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        facade.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Value
    private static class UpdateUserEmailCommand {
        String email;
    }
}
