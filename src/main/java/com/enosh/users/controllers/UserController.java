package com.enosh.users.controllers;

import com.enosh.users.dto.Dto;
import com.enosh.users.exceptions.NotExistException;
import com.enosh.users.model.User;
import com.enosh.users.response.ResponseUtils;
import com.enosh.users.service.UserService;
import com.enosh.users.utils.MessageUtils;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.enosh.users.response.ResponseUtils.*;
import static com.enosh.users.utils.MessageUtils.*;
import static java.time.LocalDateTime.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/findById/{id}")
    public ResponseEntity<Dto> findById(@PathVariable("id") Long id) {
        return responseForFind(userService.findById(id).orElse(null), id);
    }

    @PostMapping("/add")
    public ResponseEntity<Dto> add(@RequestBody User user) {
        return responseFromEither(userService.save(user));
    }

}
