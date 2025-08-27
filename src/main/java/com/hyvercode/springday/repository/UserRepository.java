package com.hyvercode.springday.repository;

import com.hyvercode.springday.model.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User,String> {
   Optional<User> findByUsername(@Param("username") String username);
}
