package com.enosh.users.controllers;

import com.enosh.users.dto.Dto;
import com.enosh.users.model.User;
import com.enosh.users.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.enosh.users.response.ResponseUtils.*;

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

    @PutMapping("/update/{id}")
    public ResponseEntity<Dto> update(@RequestBody User user, @PathVariable("id") Long id) {
        return responseFromEither(
                userService.update(
                        userService.updateFnameAndLname(
                                user.getFirstName(),
                                user.getLastName()
                        ),
                        id
                )
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Dto> delete(@PathVariable("id") Long id) {
        return responseFromEither(userService.deleteById(id));
    }



}
