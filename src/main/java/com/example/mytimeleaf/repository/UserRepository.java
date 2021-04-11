package com.example.mytimeleaf.repository;

import com.example.mytimeleaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {

}
