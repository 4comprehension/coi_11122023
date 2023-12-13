package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UserController {

    @GetMapping("/users/{id}")
    public User getUser(@PathVariable int id) throws InterruptedException {
        synchronized (new Object()) {
            Thread.sleep(10_000);
            return new User("Adam");
        }
    }

    record User(String name) {

    }
}

