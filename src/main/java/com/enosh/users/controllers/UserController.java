package com.enosh.users.controllers;

import com.enosh.users.dto.Dto;
import com.enosh.users.model.User;
import com.enosh.users.remote.JsonUtils;
import com.enosh.users.remote.RemoteRequest;
import com.enosh.users.service.UserService;
import io.vavr.Function1;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import java.time.LocalDateTime;

import static com.enosh.users.remote.JsonUtils.*;
import static com.enosh.users.response.ResponseUtils.*;
import static java.time.LocalDateTime.*;

@CrossOrigin(origins = "*")
@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RemoteRequest remoteRequest;

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

    @GetMapping("/random")
    public ResponseEntity<Dto> getRandomUser() {

        Try<ResponseEntity<Dto>> tryRandomUser = Try.of(() -> {

            JSONObject jsonObject = remoteRequest.get("https://api.randomuser.me/");
            String first = getNameAttribute(jsonObject).apply(FIRST);
            String last = getNameAttribute(jsonObject).apply(LAST);
            int age = ageFromJson(jsonObject);
            User user = new User(first, last, age);

            return ResponseEntity.ok(new Dto<>(true, now(), user));
        });

        return tryRandomUser.isSuccess() ? tryRandomUser.getOrNull() :
                ResponseEntity.ok(new Dto<>(false, now(), tryRandomUser.failed().map(Throwable::getMessage).getOrNull()));
    }


}
