package com.enosh.users.utils;

import com.enosh.users.model.BaseEntity;
import com.enosh.users.model.User;
import io.vavr.Function1;
import io.vavr.Function2;

import java.util.Scanner;
import java.util.function.Supplier;

public interface MessageUtils {

    // type ( User ) id ( Long )
    // -> operation ( String )
    // -> String result

    static <T extends BaseEntity, ID> Function2<ID, String, String> doesNotExists(Class<T> typeClass) {
        return (id, operation) -> String.format(
                "%s by the id %s does not exists in order to %s",
                typeClass.getSimpleName(),
                id,
                operation
        );
    }

    Function2<Long, String, String> userDoesNotExists = doesNotExists(User.class);

    public static void main(String[] args) {
        Function2<Long, String, String> userDoesNotExists = doesNotExists(User.class);

        System.out.println(
                userDoesNotExists.apply(4L, "delete")
        );

        System.out.println(
                userDoesNotExists.apply(3L, "update")
        );
    }
}


