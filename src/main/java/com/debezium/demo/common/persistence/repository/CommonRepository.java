package com.debezium.demo.common.persistence.repository;

import com.debezium.demo.common.persistence.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface CommonRepository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> findByStatusAndId(Status status, ID id);

    Page<T> findAllByStatus(Status status, Pageable pageable);

    boolean existsByStatusAndId(Status status, ID id);

    default Optional<T> findActiveById(ID id) {
        return findByStatusAndId(Status.ACTIVE, id);
    }

    default Optional<T> findInactiveById(ID id) {
        return findByStatusAndId(Status.INACTIVE, id);
    }

    default Page<T> findAllActive(Pageable pageable) {
        return findAllByStatus(Status.ACTIVE, pageable);
    }

    default Page<T> findAllInactive(Pageable pageable) {
        return findAllByStatus(Status.INACTIVE, pageable);
    }
}
