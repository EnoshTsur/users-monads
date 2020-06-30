package com.enosh.users.response;

import com.enosh.users.dto.Dto;
import io.vavr.API;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static io.vavr.API.*;
import static java.time.LocalDateTime.now;

public interface ResponseUtils {

    static <T> ResponseEntity<Dto> createResponse(Either<Throwable, T> either) {
               return Match(either).of(
                        Case($(Either::isRight),  ResponseEntity.ok(
                                new Dto<>(true, now(), either.right())
                        )),
                        Case($(Either::isLeft), ResponseEntity.ok(
                                new Dto<>(false, now(), either.left().map(Throwable::getMessage))
                        ))
                );
    }
}
