package com.debezium.demo.web.controller;

import com.debezium.demo.course.dto.CourseDto;
import com.debezium.demo.course.facade.CourseFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/courses")
public class CourseController {

    private final CourseFacade courseFacade;

    @GetMapping("/{id}")
    public ResponseEntity<CourseDto> read(@PathVariable Long id) {
        return ResponseEntity.ok(courseFacade.read(id));
    }

    @GetMapping
    public ResponseEntity<Page<CourseDto>> findAllActive(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok(courseFacade.findAllActive(pageable));
    }

    @PostMapping
    public ResponseEntity<CourseDto> create(@Valid @RequestBody CourseDto courseDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(courseFacade.create(courseDto));
    }

    @PutMapping
    public ResponseEntity<CourseDto> update(CourseDto courseDto) {
        return ResponseEntity.ok(courseFacade.update(courseDto));
    }

    @PatchMapping("/{courseId}/{userId}/add-author")
    public ResponseEntity<CourseDto> addAuthor(Long courseId, Long userId) {
        return ResponseEntity.ok(courseFacade.addAuthor(courseId, userId));
    }

    @PatchMapping("/{courseId}/{userId}/add-member")
    public ResponseEntity<CourseDto> addMember(Long courseId, Long userId) {
        return ResponseEntity.ok(courseFacade.addMember(courseId, userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        courseFacade.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
