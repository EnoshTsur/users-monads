package com.enosh.users;

import com.enosh.users.dto.Dto;
import com.enosh.users.model.User;
import com.enosh.users.response.ResponseUtils;
import com.enosh.users.service.JpaService;
import com.enosh.users.service.UserService;
import io.vavr.API;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.Objects;

import static io.vavr.API.*;
import static io.vavr.Predicates.*;
import static java.time.LocalDateTime.*;

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

    @Test
    void match(){
        String a = Match("koby").of(
                Case($("dsf"), ""),
                Case($(is( "koby")), "hahah"),
                Case($(x -> x.contains("s")), "asda"),
                Case($(isIn("koby", "f", "f2")), "sadasd"),
                Case($(isNull()), "noooo!!"),
                Case($(), "default")
        );

        System.out.println(a);
    }
}
