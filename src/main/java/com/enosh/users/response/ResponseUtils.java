package com.enosh.users.response;

import com.enosh.users.dto.Dto;
import io.vavr.API;
import io.vavr.control.Either;
import io.vavr.control.Option;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.function.Function;

import static io.vavr.API.*;
import static java.time.LocalDateTime.now;

public interface ResponseUtils {

    private static <ID> String noUserById(ID id) {
        return "No user by the id " + id;
    }

    static <T> ResponseEntity<Dto> responseFromEither(Either<Throwable, T> either) {
               return Match(either).of(
                        Case($(Either::isRight),  ResponseEntity.ok(
                                new Dto<>(true, now(), either.right().get())
                        )),
                        Case($(Either::isLeft), ResponseEntity.ok(
                                new Dto<>(false, now(), either.left().map(Throwable::getMessage))
                        ))
                );
    }

    static <T, ID> ResponseEntity<Dto> responseForFind(T maybe, ID id) {
        return maybe != null ?
                ResponseEntity.ok(new Dto<>(true, now(), maybe)) :
                ResponseEntity.ok(new Dto<>(false, now(), noUserById(id)));
    }
}
