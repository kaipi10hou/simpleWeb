package com.example.mythymeleaf.repository;

import com.example.mythymeleaf.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long>, QuerydslPredicateExecutor<User>, CustomizedUserRepository{

    @EntityGraph(attributePaths = {"boards"})
    List<User> findAll();

    User findByUsername(String username);

    @Query("select u from User u where u.username like %?1%")
    List<User> findByUsernameQuery(String username);
}
