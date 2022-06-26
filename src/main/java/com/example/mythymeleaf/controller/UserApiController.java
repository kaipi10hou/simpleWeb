package com.example.mythymeleaf.controller;

import com.example.mythymeleaf.model.Board;
import com.example.mythymeleaf.model.QUser;
import com.example.mythymeleaf.model.User;
import com.example.mythymeleaf.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api")
class UserApiController {

    private final UserRepository repository;

    UserApiController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/users")
    Iterable<User> all(@RequestParam(required = false) String method, String text){
        Iterable<User> users = null;
        if ("query".equals(method)) {
            users = repository.findByUsernameQuery(text);
        } else if ("querydsl".equals(method)) {
            QUser user = QUser.user;
            Predicate predicate = user.username.contains(text);
            users = repository.findAll(predicate);
        } else if ("querydsl-custom".equals(method)){
            users = repository.findByUsernameCustom(text);
        } else if ("jdbc".equals(method)){
            users = repository.findByUsernameJDBC(text);
        } else {
            users = repository.findAll();
        }
        return users;
    }

    @PostMapping("/user")
    User newUser(@RequestBody User newUser) {
        return repository.save(newUser);
    }

    // Single item

    @GetMapping("/user/{id}")
    User one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/user/{id}")
    User replaceEmployee(@RequestBody User newUser, @PathVariable Long id) {

        return repository.findById(id)
                .map(user -> {
//                    user.setTitle(newUser.getTitle());
//                    user.setContent(newUser.getContent());
                    user.setBoards(newUser.getBoards());
                    for (Board board : user.getBoards()) {
                        board.setUser(user);
                    }
                    return repository.save(user);
                })
                .orElseGet(() -> {
                    newUser.setId(id);
                    return repository.save(newUser);
                });
    }

    @DeleteMapping("/user/{id}")
    void deleteUser(@PathVariable Long id) {
        repository.deleteById(id);
    }
}