package com.debezium.demo.user.persistence.repository;

import com.debezium.demo.common.persistence.repository.CommonRepository;
import com.debezium.demo.user.persistence.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CommonRepository<User, Long> {

}
