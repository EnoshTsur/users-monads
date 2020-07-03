package com.enosh.users.controllers;

import com.enosh.users.dto.Dto;
import com.enosh.users.model.User;
import com.enosh.users.remote.JsonUtils;
import com.enosh.users.remote.RemoteRequest;
import com.enosh.users.service.UserService;
import io.vavr.Function1;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

import static com.enosh.users.remote.JsonUtils.*;
import static com.enosh.users.response.ResponseUtils.*;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final RemoteRequest remoteRequest;

    @PostConstruct
    private void init(){
        JSONObject jsonObject = remoteRequest.get("https://api.randomuser.me/");
        Function1<String, String> getName = getNameAttribute(jsonObject);
        String first = getName.apply(FIRST);
        String last = getName.apply(LAST);
        int age = ageFromJson(jsonObject);
        User user = new User(first, last, age);


        System.out.println("\n\n\n\n\n\n\n\n\n" + user + "\n\n\n\n\n\n\n\n\n\n");
    }

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
