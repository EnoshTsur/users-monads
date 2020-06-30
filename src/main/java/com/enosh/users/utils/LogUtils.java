package com.enosh.users.utils;

import com.enosh.users.exceptions.NotExistException;
import com.enosh.users.model.BaseEntity;
import io.vavr.control.Either;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

import static com.enosh.users.utils.MessageUtils.*;

@Slf4j
public class LogUtils {

    public static  <T> void printSuccess(T t) {
        log.info("Success: {}", t);
    }

    public static void printError(Throwable throwable) {
        log.error("Error: {}", throwable.getMessage());
    }



//    public static  <T> void tryOnSuccessAndFailure(Try<T> tryOf) {
//        tryOf.onSuccess(LogUtils::printSuccess);
//        tryOf.onFailure(LogUtils::printError);
//    }
}
