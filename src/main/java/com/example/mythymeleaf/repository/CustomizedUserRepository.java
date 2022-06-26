package com.example.mythymeleaf.repository;

import com.example.mythymeleaf.model.User;

import java.util.List;

public interface CustomizedUserRepository {
    List<User> findByUsernameCustom(String username);
    List<User> findByUsernameJDBC(String username);
}
