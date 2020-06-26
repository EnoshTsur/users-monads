package com.enosh.users;

import com.enosh.users.model.User;
import com.enosh.users.service.JpaService;
import com.enosh.users.service.UserService;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UsersTests {

    @Resource
    private UserService userService;

    @Test
    void contextLoads() {
    }

    @Test
    void saveUser() {

    }

}
