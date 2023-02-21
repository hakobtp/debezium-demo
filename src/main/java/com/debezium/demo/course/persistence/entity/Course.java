package com.debezium.demo.course.persistence.entity;

import com.debezium.demo.common.persistence.entity.AbstractModificationInfoBaseEntity;
import com.debezium.demo.course.eventlistner.CourseEntityEventListener;
import com.debezium.demo.user.persistence.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "courses")
@Accessors(chain = true)
@EntityListeners(CourseEntityEventListener.class)
public class Course extends AbstractModificationInfoBaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_authors",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id")}
    )
    private List<User> authors = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_members",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "member_id")}
    )
    private List<User> members = new ArrayList<>();

    public void addAuthor(User user) {
        Assert.notNull(user.getId(), "author not saved");
        authors.add(user);
    }

    public void addMembers(User user) {
        Assert.notNull(user.getId(), "member not saved");
        members.add(user);
    }

    @Override
    public Course inActive() {
        return (Course) super.inActive();
    }
}
